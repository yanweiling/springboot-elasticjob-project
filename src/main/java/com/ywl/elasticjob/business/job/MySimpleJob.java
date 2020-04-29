package com.ywl.elasticjob.business.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ywl.elasticjob.autoconfig.ElasticSimpleJob;
import lombok.extern.slf4j.Slf4j;
@ElasticSimpleJob(jobName = "mySimpleJob",cron = "0/10 * * * * ?",shardingTotalCount = 1,overwrite = true)
@Slf4j
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("我是分片项:{}总分片项是：{}",shardingContext.getShardingItem(),shardingContext.getShardingTotalCount());
    }
}
