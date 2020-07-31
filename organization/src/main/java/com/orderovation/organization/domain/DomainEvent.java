package com.orderovation.organization.domain;

import org.springframework.context.ApplicationEvent;

/**
 * @author devin
 */
public class DomainEvent extends ApplicationEvent {
    public DomainEvent(Object source) {
        super(source);
    }
}
