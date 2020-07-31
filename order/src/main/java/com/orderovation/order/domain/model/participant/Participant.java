package com.orderovation.order.domain.model.participant;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * @author devin
 */
@Embeddable
@MappedSuperclass
public class Participant {
    private String id;
    private String name;
    private String serialNumber;
    private String email;

    public Participant() {}

    public Participant(String id, String name, String serialNumber, String email) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
