package com.ywl.elasticjob;

import com.ywl.elasticjob.business.SpringbootElasticjobApplication;
import com.ywl.elasticjob.business.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringbootElasticjobApplication.class)
@RunWith(SpringRunner.class)
public class MyTest {
    @Autowired
    private OrderService orderService;


    @Test
    public void testOrder(){
        orderService.insertOrder();
    }
}
