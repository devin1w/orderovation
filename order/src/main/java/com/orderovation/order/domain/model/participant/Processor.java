package com.orderovation.order.domain.model.participant;

import com.orderovation.order.domain.model.order.Order;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * @author devin
 */
@Embeddable
@MappedSuperclass
public class Processor extends Participant {
    public Processor() {
        super();
    }

    public Processor(String id, String name, String serialNumber, String email) {
        super(id, name, serialNumber, email);
    }

    public void receive(Order order) {
        order.setProcessor(this);
    }
}
