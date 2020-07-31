package com.orderovation.organization.domain.model.project;

import com.orderovation.organization.domain.model.member.Manager;
import org.springframework.util.StringUtils;

import javax.persistence.*;

/**
 * @author devin
 */
@Entity
@Table(name = "oo_project")
public class Project {
    @Id
    private String id;
    private String name;
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "manager_id")),
            @AttributeOverride(name = "name", column = @Column(name = "manager_name")),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "manager_serial_number")),
            @AttributeOverride(name = "email", column = @Column(name = "manager_email"))
    })
    private Manager manager;

    public Project() {
    }

    public Project(String id, String name, Manager manager) {
        this.setId(id);
        this.setName(name);
        this.setManager(manager);
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
        if (StringUtils.isEmpty(name)) {
            throw new IllegalStateException("The name may not be set to null");
        }
        this.name = name;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        if (manager == null) {
            throw new IllegalStateException("The manager may not be set to null");
        }
        this.manager = manager;
    }
}
