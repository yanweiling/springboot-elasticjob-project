package com.ywl.elasticjob.autoconfig;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("elasticjob.zookeeper.server-list")
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperAutoConfig {
    @Autowired
    ZookeeperProperties zookeeperProperties;

    /**
     * 创建zookeeper注册中心
     * @return
     */
    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter zkCenter(){
        String serverList=zookeeperProperties.getServerList();
        String namespace=zookeeperProperties.getNamespace();
        ZookeeperConfiguration configuration=new ZookeeperConfiguration(serverList,namespace);
        ZookeeperRegistryCenter registryCenter=new ZookeeperRegistryCenter(configuration);
        //注册中心初始化
//        registryCenter.init();  //可以改为注解方式
        return registryCenter;
    }
}
