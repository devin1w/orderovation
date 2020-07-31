package com.orderovation.organization.domain.model.member;

import com.orderovation.organization.domain.model.project.Project;
import com.orderovation.organization.domain.model.project.team.Team;

public class Admin extends Member {

    public Admin(String id, String name, String serialNumber, String email) {
        super(id, name, serialNumber, email);
    }

    public Project createProject(String id, String name, Manager manager) {
        return new Project(id, name, manager);
    }

    public void changeProjectManager(Project project, Manager manager) {
        project.setManager(manager);
    }

    public void changeProjectName(Project project, String name) {
        project.setName(name);
    }

    public void moveTeamToProject(Team team, String projectId) {
        team.setProjectId(projectId);
    }
}
