package com.orderovation.organization.ui.dto;

/**
 * @author devin
 */
public class ProjectFormDTO {
    private String adminId;
    private String id;
    private String name;
    private String managerId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
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

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
                "projectId=" + id +
                ", name='" + name + '\'' +
                ", managerId=" + managerId +
                '}';
    }
}
