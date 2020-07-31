package com.orderovation.organization.domain.model.project.team.teammember;

import com.orderovation.organization.domain.model.member.Member;

import javax.persistence.*;

@Entity
@Table(name = "oo_team_member")
public class TeamMember {
    @Id
    private String id;
    private String teamId;
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "member_id"))
    })
    private Member member;

    public TeamMember() {
    }

    public TeamMember(String id, String teamId, Member member) {
        this.setId(id);
        this.setTeamId(teamId);
        this.setMember(member);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        if (teamId == null) {
            throw new IllegalStateException("The teamId may not be set to null");
        }
        this.teamId = teamId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        if (member == null) {
            throw new IllegalStateException("The member may not be set to null");
        }
        this.member = member;
    }
}
