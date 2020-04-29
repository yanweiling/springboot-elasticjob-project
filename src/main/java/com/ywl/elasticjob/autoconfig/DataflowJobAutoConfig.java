package com.ywl.elasticjob.autoconfig;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
@ConditionalOnBean(CoordinatorRegistryCenter.class)
@AutoConfigureAfter(ZookeeperAutoConfig.class)
public class DataflowJobAutoConfig {
    @Autowired
    CoordinatorRegistryCenter zkCenter;

    @Autowired
    ApplicationContext context;

    @PostConstruct
    public void initDataFlowjob(){
        Map<String,Object> beans=context.getBeansWithAnnotation(ElasticDataflowJob.class);
        for(Map.Entry<String,Object> entry : beans.entrySet()){
            Object instance=entry.getValue();
            Class<?>[] interfaces=instance.getClass().getInterfaces();
            for(Class<?> superInterface : interfaces){
                if(superInterface== DataflowJob.class){
                    //说明是需要注册的dataflowjob
                    ElasticDataflowJob annotaion=instance.getClass().getAnnotation(ElasticDataflowJob.class);
                    if(!annotaion.isStart())continue;
                    String jobName=annotaion.jobName();
                    String cron=annotaion.cron();
                    int shardingTotalCount=annotaion.shardingTotalCount();
                    boolean overwrite=annotaion.overwrite();
                    boolean streamingProcess=annotaion.streamingProcess();

                    //job核心配置
                    JobCoreConfiguration jcc = JobCoreConfiguration
                            .newBuilder(jobName,cron,shardingTotalCount)
                            .build();

                    //job类型配置
                    DataflowJobConfiguration jtc = new DataflowJobConfiguration(jcc,
                            instance.getClass().getCanonicalName(),streamingProcess);

                    //job根的配置（LiteJobConfiguration）
                    LiteJobConfiguration ljc = LiteJobConfiguration
                            .newBuilder(jtc)
                            .overwrite(overwrite)
                            .build();

                    //然后注册到shedule中
                    new JobScheduler(zkCenter,ljc).init();

                }
            }
        }
    }

}
