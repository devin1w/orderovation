package com.orderovation.order.domain.model.order;

import com.orderovation.order.domain.model.participant.Participant;
import com.orderovation.order.domain.model.DomainEvent;

/**
 * 工单流转事件
 * @author devin
 */
public class OrderTransferedEvent extends DomainEvent {

    private Order order;
    private String content;
    private Participant sender;
    private Participant receiver;

    public OrderTransferedEvent(Order order, Participant sender, Participant receiver, String content) {
        super(order);
        this.setOrder(order);
        this.setContent(content);
        this.setSender(sender);
        this.setReceiver(receiver);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Participant getSender() {
        return sender;
    }

    public void setSender(Participant sender) {
        if (sender == null) {
            throw new IllegalArgumentException("The sender may not be set to null.");
        }
        this.sender = sender;
    }

    public Participant getReceiver() {
        return receiver;
    }

    public void setReceiver(Participant receiver) {
        if (receiver == null) {
            throw new IllegalArgumentException("The receiver may not be set to null.");
        }
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null) {
            throw new IllegalArgumentException("The content may not be set to null.");
        }
        this.content = content;
    }
}
