package com.orderovation.order.domain.model.participant;

/**
 * @author devin
 */
public class Reviewer extends Processor {

    public Reviewer() {
        super();
    }

    public Reviewer(String id, String name, String serialNumber, String email) {
        super(id, name, serialNumber, email);
    }
}
