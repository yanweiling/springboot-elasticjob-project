package com.ywl.elasticjob.business.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ywl.elasticjob.autoconfig.ElasticSimpleJob;
import com.ywl.elasticjob.business.model.Order;
import com.ywl.elasticjob.business.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ElasticSimpleJob(
        jobName = "orderCancelJob",
        cron = "0/15 * * * * ?",
        shardingTotalCount = 2,
        overwrite = true
)
@Slf4j
public class OrderCancelJob  implements SimpleJob {

    @Autowired
    private OrderService orderService;
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("我是分片项:{}总分片项是：{}",shardingContext.getShardingItem(),shardingContext.getShardingTotalCount());
        log.info("QX取消订单....begin");
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND,-30);
        //订单尾号 % 分片总数 == 当前分片项
        List<Order> orders = orderService.getOrder(now,
                shardingContext.getShardingTotalCount(),shardingContext.getShardingItem());

        if (orders!=null&&orders.size()>0){
            ExecutorService es = Executors.newFixedThreadPool(4);
            for(Order order : orders){
                es.execute(()->{
                    //更新条件
                    Integer orderId = order.getId();
                    Date updateTime = order.getUpdateTime();
                    //更新内容
                    int status = 3;//已取消
                    String updateUser = "system";
                    Date updateNow = new Date();

                    orderService.cancelOrder(orderId,updateTime,status,updateUser,updateNow);
                });
            }
            es.shutdown();
        }

        log.info("QX取消订单....end");
    }
}
