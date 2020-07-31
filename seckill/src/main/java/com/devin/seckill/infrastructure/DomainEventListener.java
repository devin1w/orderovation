package com.devin.seckill.infrastructure;

import com.devin.seckill.application.OrderApplicationService;
import com.devin.seckill.domain.model.order.OrderToCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

/**
 * 领域事件监听器
 * @author devin
 */
@Configuration
public class DomainEventListener {

    @Autowired
    private OrderApplicationService orderApplicationService;

    /**
     * 新增订单
     */
    @Bean
    public ApplicationListener<OrderToCreateEvent> createOrderListener() {
        return orderToCreateEvent -> {
            try {
                orderApplicationService.createOrder(orderToCreateEvent.getCustomerId(), orderToCreateEvent.getCommodityId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 同步库存
     */
    @Bean
    public ApplicationListener<OrderToCreateEvent> syncCommodityStockListener() {
        return orderToCreateEvent -> {
            try {
                orderApplicationService.syncCommodityStock(orderToCreateEvent.getCommodityId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}
