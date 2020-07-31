package com.orderovation.order.domain.model.order;

import com.orderovation.order.domain.model.order.Order;
import com.orderovation.order.domain.model.DomainEvent;

/**
 * 工单发起事件
 * @author devin
 */
public class OrderInitiatedEvent extends DomainEvent {

    private Order order;

    public OrderInitiatedEvent(Order order) {
        super(order);
        this.setOrder(order);
    }

    private void setOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("The order may not be set to null.");
        }
        this.order = order;
    }

    public Order getOrder() {
        return this.order;
    }
}
