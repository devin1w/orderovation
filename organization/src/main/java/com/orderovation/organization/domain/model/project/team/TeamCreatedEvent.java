package com.orderovation.organization.domain.model.project.team;

import com.orderovation.organization.domain.DomainEvent;

/**
 * @author devin
 */
public class TeamCreatedEvent extends DomainEvent {

    private Team team;

    public TeamCreatedEvent(Team newTeam) {
        super(newTeam);
        this.setTeam(newTeam);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        if (team == null) {
            throw new IllegalStateException("The team may not be set to null");
        }
        this.team = team;
    }
}
