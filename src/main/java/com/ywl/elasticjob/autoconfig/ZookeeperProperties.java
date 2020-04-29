package com.ywl.elasticjob.autoconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "elasticjob.zookeeper")
@Getter@Setter
public class ZookeeperProperties {
    //zookeeper注册列表
    private String serverList;
    //zookeeper命名空间
    private String namespace;
}
