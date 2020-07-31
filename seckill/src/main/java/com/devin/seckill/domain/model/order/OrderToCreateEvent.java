package com.devin.seckill.domain.model.order;

import com.devin.seckill.domain.model.DomainEvent;
import org.springframework.util.StringUtils;

public class OrderToCreateEvent extends DomainEvent {

    private String customerId;
    private String commodityId;
    private Order order;

    public OrderToCreateEvent(String customerId, String id) {
        super(id);
        this.setCommodityId(id);
        this.setCustomerId(customerId);
    }

    public OrderToCreateEvent(Order order) {
        super(order);
        this.setOrder(order);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
