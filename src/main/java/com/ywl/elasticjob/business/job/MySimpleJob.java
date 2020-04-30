package com.ywl.elasticjob.business.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ywl.elasticjob.autoconfig.ElasticSimpleJob;
import com.ywl.elasticjob.autoconfig.ZookeeperProperties;
import com.ywl.elasticjob.business.service.OrderService;
import com.ywl.elasticjob.business.sharding.MyShardingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@ElasticSimpleJob(jobName = "mySimpleJob",
        cron = "0/5 * * * * ?",
        shardingTotalCount = 10,
        overwrite = true,
        jobStrategy = MyShardingStrategy.class
)
@Slf4j
public class MySimpleJob implements SimpleJob {

    @Autowired
    OrderService orderService;
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("我是分片项:{}总分片项是：{}",shardingContext.getShardingItem(),shardingContext.getShardingTotalCount());
//        log.info("创建订单....begin");
//        for (int i=0;i<10;i++){
//            orderService.insertOrder();
//        }
//        log.info("创建订单....end");
    }
}
