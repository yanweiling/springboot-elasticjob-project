package com.ywl.elasticjob.autoconfig;

import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ElasticSimpleJob {
    String jobName() default "";
    String cron() default "";
    int shardingTotalCount() default 1;
    boolean overwrite() default false;//更改信息是否要覆盖到zookeeper上
    boolean isStart() default true;
    Class<? extends JobShardingStrategy> jobStrategy() default AverageAllocationJobShardingStrategy.class;
    boolean jobEvent() default false;//是否时间追踪
}
