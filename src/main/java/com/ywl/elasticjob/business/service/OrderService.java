package com.ywl.elasticjob.business.service;

import com.ywl.elasticjob.business.dao.OrderMapper;
import com.ywl.elasticjob.business.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Date;

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

}
