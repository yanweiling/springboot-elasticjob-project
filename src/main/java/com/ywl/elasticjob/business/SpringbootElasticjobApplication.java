package com.ywl.elasticjob.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ywl.elasticjob.business.dao")
public class SpringbootElasticjobApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootElasticjobApplication.class, args);
    }

}
