package com.orderovation.order.ui.dto;

public class SubmitOrderDTO {
    private String initiatorId;
    private String projectId;
    private Integer orderType;
    private String title;
    private String description;

    public String getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(String initiatorId) {
        this.initiatorId = initiatorId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SubmitOrderDTO{" +
                "initiatorId='" + initiatorId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", orderType=" + orderType +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
