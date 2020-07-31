package com.orderovation.order.domain.model.order;

import com.orderovation.order.domain.model.participant.Initiator;
import com.orderovation.order.domain.model.participant.Processor;
import com.orderovation.order.domain.model.participant.Reviewer;
import com.orderovation.order.domain.model.DomainEventPublisher;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "oo_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderType;
    private String projectId;
    private String title;
    private String description;
    private Integer status;
    private Date createDate;
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "initiator_id")),
            @AttributeOverride(name = "name", column = @Column(name = "initiator_name")),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "initiator_serial_number")),
            @AttributeOverride(name = "email", column = @Column(name = "initiator_email"))
    })
    private Initiator initiator;
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "processor_id")),
            @AttributeOverride(name = "name", column = @Column(name = "processor_name")),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "processor_serial_number")),
            @AttributeOverride(name = "email", column = @Column(name = "processor_email"))
    })
    private Processor processor;

    public Order() {
    }

    public Order(String projectId, Integer orderType, String title, String description) {
        this();
        this.setProjectId(projectId);
        this.setOrderType(orderType);
        this.setTitle(title);
        this.setDescription(description);
        this.setStatus(OrderStatus.NEW);
        this.setCreateDate(new Date());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Initiator getInitiator() {
        return initiator;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void setOrderType(Integer orderType) {
        if (orderType == null) {
            throw new IllegalArgumentException("The orderType may not be set to null.");
        }
        this.orderType = orderType;
    }

    public void setProjectId(String projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("The projectId may not be set to null.");
        }
        this.projectId = projectId;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("The title may not be set to null.");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Integer status) {
        if (status == null) {
            throw new IllegalArgumentException("The status may not be set to null.");
        }
        this.status = status;
    }

    public void setCreateDate(Date createDate) {
        if (createDate == null) {
            throw new IllegalArgumentException("The createDate may not be set to null.");
        }
        this.createDate = createDate;
    }

    public void setInitiator(Initiator initiator) {
        if (initiator == null) {
            throw new IllegalArgumentException("The initiator may not be set to null.");
        }
        this.initiator = initiator;
    }

    // 行为方法

    public void submitTo(Reviewer reviewer) {
        this.setStatus(OrderStatus.AUDITING);
        this.setProcessor(reviewer);
        DomainEventPublisher.publisher().publishEventAfterCommit(
                new OrderTransferedEvent(this, getInitiator(), getProcessor(), "提交新工单"));
    }
}
