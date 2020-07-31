package com.orderovation.organization.domain.model.member;

import com.orderovation.organization.domain.model.project.team.teammember.TeamMember;

import javax.persistence.Embeddable;

@Embeddable
public class Leader extends Member {

    public Leader(Member member) {
        super(member);
    }

    public Leader() {
    }

    public Leader(String id, String name, String serialNumber, String email) {
        super(id, name, serialNumber, email);
    }

    public TeamMember addMember(String id, String teamId, Member member) {
        return new TeamMember(id, teamId, member);
    }
}
