package com.orderovation.order.domain.model;

import org.springframework.context.ApplicationEvent;

/**
 * @author devin
 */
public class DomainEvent extends ApplicationEvent {
    public DomainEvent(Object source) {
        super(source);
    }
}
