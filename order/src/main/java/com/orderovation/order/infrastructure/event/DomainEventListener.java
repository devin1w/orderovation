package com.orderovation.order.infrastructure.event;

import com.orderovation.order.application.OrderApplicationService;
import com.orderovation.order.domain.model.order.OrderInitiatedEvent;
import com.orderovation.order.domain.model.order.OrderTransferedEvent;
import com.orderovation.order.infrastructure.exception.ContextAdapterException;
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
     * 工单发起事件：更新当前处理者
     */
    @Bean
    public ApplicationListener<OrderInitiatedEvent> orderFirstTransferListener() {
        return new ApplicationListener<OrderInitiatedEvent>() {
            @Override
            @Async
            public void onApplicationEvent(OrderInitiatedEvent orderInitiatedEvent) {
                try {
                    orderApplicationService.initiationSubmit(orderInitiatedEvent.getOrder().getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 工单流转事件：新增流转记录
     */
    @Bean
    public ApplicationListener<OrderTransferedEvent> updateProcessorListener() {
        return new ApplicationListener<OrderTransferedEvent>() {
            @Override
            @Async
            public void onApplicationEvent(OrderTransferedEvent orderTransferedEvent) {
                orderApplicationService.createTransferLog(
                        orderTransferedEvent.getOrder().getId(),
                        orderTransferedEvent.getContent(),
                        orderTransferedEvent.getSender(),
                        orderTransferedEvent.getReceiver());
            }
        };
    }
}
