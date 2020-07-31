package com.devin.seckill.domain.model.order;

import com.devin.seckill.application.BusiException;
import com.devin.seckill.domain.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    public String nextIdentity() {
        return UUID.randomUUID().toString();
    }

    public Order newOrder(String customerId, String id) {
        return new Order(nextIdentity(), customerId, id, new Date());
    }
}
