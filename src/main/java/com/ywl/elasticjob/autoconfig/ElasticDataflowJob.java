package com.ywl.elasticjob.autoconfig;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ElasticDataflowJob {
    String jobName() default "";
    String cron() default "";
    int shardingTotalCount() default 1;
    boolean overwrite() default false;
    boolean streamingProcess() default false;
    boolean isStart() default true;
}
