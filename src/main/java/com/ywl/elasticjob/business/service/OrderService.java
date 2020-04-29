package com.ywl.elasticjob.business.service;

import com.ywl.elasticjob.business.dao.OrderMapper;
import com.ywl.elasticjob.business.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderMapper orderMapper;

    public int insertOrder(){
        Order order = new Order();
        order.setAmount(BigDecimal.TEN);
        order.setReceiveName("Green");
        order.setReceiveAddress("中国北京朝阳区xxx");
        order.setReceiveMobile("13811112222");
        order.setStatus(1);//未支付
        order.setCreateUser("Green");
        order.setCreateTime(new Date());
        order.setUpdateUser("Green");
        order.setUpdateTime(new Date());
        int i = orderMapper.insertSelective(order);
        return i;
    }


    public List<Order> getOrder(Calendar now, int shardingTotalCount, int shardingItem) {
        return orderMapper.getOrder(now.getTime(),shardingTotalCount,shardingItem);
    }

    public void cancelOrder(Integer orderId, Date updateTime, int status, String updateUser, Date updateNow) {
        orderMapper.cancelOrder(orderId,updateTime,status,updateUser,updateNow);
    }
}
