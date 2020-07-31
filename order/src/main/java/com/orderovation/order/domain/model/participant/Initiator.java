package com.orderovation.order.domain.model.participant;

import com.orderovation.order.domain.model.order.OrderInitiatedEvent;
import com.orderovation.order.domain.model.order.Order;
import com.orderovation.order.domain.model.DomainEventPublisher;

import javax.persistence.Embeddable;

/**
 * @author devin
 */
@Embeddable
public class Initiator extends Participant {
    public Initiator(String id, String name, String serialNumber, String email) {
        super(id, name, serialNumber, email);
    }

    public Initiator() {
        super();
    }

    // 行为方法

    public void initiate(Order order) {
        order.setInitiator(this);
        DomainEventPublisher.publisher().publishEventAfterCommit(new OrderInitiatedEvent(order));
    }
}
