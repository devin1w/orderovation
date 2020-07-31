package com.orderovation.order.domain.model.order.transfer;

import com.orderovation.order.domain.model.participant.Participant;
import com.orderovation.order.domain.model.participant.Processor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author devin
 */
@Entity
@Table(name = "oo_transfer_log")
public class TransferLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderId;
    private String content;
    private Date transferDate;
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "sender_id")),
            @AttributeOverride(name = "name", column = @Column(name = "sender_name")),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "sender_serial_number")),
            @AttributeOverride(name = "email", column = @Column(name = "sender_email"))
    })
    private Participant sender;
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "receiver_id")),
            @AttributeOverride(name = "name", column = @Column(name = "receiver_name")),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "receiver_serial_number")),
            @AttributeOverride(name = "email", column = @Column(name = "receiver_email"))
    })
    private Participant receiver;

    public TransferLog() {}

    public TransferLog(Integer orderId, String content, Participant sender, Participant receiver) {
        this.orderId = orderId;
        this.content = content;
        this.transferDate = new Date();
        this.sender = sender;
        this.receiver = receiver;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Participant getSender() {
        return sender;
    }

    public void setSender(Participant sender) {
        this.sender = sender;
    }

    public Participant getReceiver() {
        return receiver;
    }

    public void setReceiver(Participant receiver) {
        this.receiver = receiver;
    }
}
