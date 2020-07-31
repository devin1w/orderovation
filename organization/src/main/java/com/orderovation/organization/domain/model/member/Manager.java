package com.orderovation.organization.domain.model.member;

import com.orderovation.organization.domain.model.project.team.Team;
import com.orderovation.organization.domain.model.project.team.TeamType;

import javax.persistence.Embeddable;

@Embeddable
public class Manager extends Member {
    public Manager() {
    }

    public Manager(String id, String name, String serialNumber, String email) {
        super(id, name, serialNumber, email);
    }

    public Team createTeam(String id, String name, String projectId, TeamType teamType, Leader leader) {
        return new Team(id, name, projectId, teamType, leader);
    }

    public void changeTeamLeader(Team team, Leader leader) {
        team.setLeader(leader);
    }

    public void changeTeamName(Team team, String name) {
        team.setName(name);
    }

    public void changeTeamType(Team team, TeamType teamType) {
        team.setTeamType(teamType);
    }
}
