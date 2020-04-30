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
public @interface ElasticDataflowJob {
    String jobName() default "";
    String cron() default "";
    int shardingTotalCount() default 1;
    boolean overwrite() default false;
    boolean streamingProcess() default false;
    boolean isStart() default true;
    Class<? extends JobShardingStrategy> jobStrategy() default AverageAllocationJobShardingStrategy.class;
    /*esjob提供了任务事件追踪功能，通过做数据源配置，监听事件，会在任务执行的时候在指定的数据源创建俩张表，
    job_execution_log， 和 job_status_trace_log
    * */
    boolean jobEvent() default false;//是否时间追踪
}
