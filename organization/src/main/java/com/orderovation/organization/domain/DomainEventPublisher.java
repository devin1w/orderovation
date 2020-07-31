package com.orderovation.organization.domain;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 领域事件发布器
 * @author devin
 */
@Component(value = "domainEventPublisher")
public class DomainEventPublisher implements ApplicationEventPublisherAware, ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (DomainEventPublisher.applicationContext == null) {
            DomainEventPublisher.applicationContext = applicationContext;
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public static DomainEventPublisher publisher() {
        return (DomainEventPublisher) applicationContext.getBean("domainEventPublisher");
    }

    public void publishEvent(DomainEvent domainEvent) {
        eventPublisher.publishEvent(domainEvent);
    }

    public void publishEventAfterCommit(DomainEvent domainEvent) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                eventPublisher.publishEvent(domainEvent);
            }
        });
    }
}
