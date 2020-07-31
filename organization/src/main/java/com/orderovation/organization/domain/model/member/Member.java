package com.orderovation.organization.domain.model.member;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Embeddable
public class Member {
    private String id;
    private String name;
    private String serialNumber;
    private String email;

    public Member() {
    }

    public Member(Member other) {
        this.setId(other.getId());
        this.setName(other.getName());
        this.setSerialNumber(other.getSerialNumber());
        this.setEmail(other.getEmail());
    }

    public Member(String id, String name, String serialNumber, String email) {
        this.setId(id);
        this.setName(name);
        this.setSerialNumber(serialNumber);
        this.setEmail(email);
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
