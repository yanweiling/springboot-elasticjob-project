package com.ywl.elasticjob.autoconfig;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.ywl.elasticjob.business.job.MySimpleJob;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 用来将simpleJob任务自动注册到zookeeper注册中心上
 */
//首先它是一个配置了，所以加上@Configuration
@Configuration
//然后是启动条件:如果发现了注册中心，我们就启动
@ConditionalOnBean(CoordinatorRegistryCenter.class)
//保证在zookeeper自动注册实例化以后
@AutoConfigureAfter(ZookeeperAutoConfig.class)
public class SimpleJobAutoConfig {
    //注入协调注册中心
    @Autowired
    CoordinatorRegistryCenter registryCenter;
    @Autowired
    ApplicationContext context;

    @PostConstruct
    public void initSimpleJob(){
        //获得被注解ElasticSimpleJob修饰过的bean
        Map<String,Object> beans=context.getBeansWithAnnotation(ElasticSimpleJob.class);
        for(Map.Entry<String,Object> entry : beans.entrySet()){
            Object instance=entry.getValue();
            //避免开发人员错误的时候了elasticSimpleJob注解，继而判断是否实现了simpleJob接口
            Class<?>[] interfaces= instance.getClass().getInterfaces();
            for(Class<?> superInterface : interfaces){
                if(superInterface== SimpleJob.class){//说明这个类是simple任务
                    //获得注解
                    ElasticSimpleJob annotation=instance.getClass().getAnnotation(ElasticSimpleJob.class);
                    if(!annotation.isStart())continue;
                    String jobName=annotation.jobName();
                    String cron=annotation.cron();
                    int shardingTotalCount=annotation.shardingTotalCount();
                    boolean overwrite=annotation.overwrite();
                    //job核心配置
                    JobCoreConfiguration coreConfiguration=JobCoreConfiguration.newBuilder(jobName,cron,shardingTotalCount).build();

                    //job类型配置
                    SimpleJobConfiguration jobConfiguration=new SimpleJobConfiguration(coreConfiguration,instance.getClass().getCanonicalName());
                    //job的根配置
                    LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration
                .newBuilder(jobConfiguration)
                .overwrite(overwrite)
                .build();

                    //然后注册到shedule中
//                new JobScheduler(registryCenter,liteJobConfiguration).init();
                new SpringJobScheduler((ElasticJob) instance,registryCenter,liteJobConfiguration).init();

    }
}
        }

    }
}
