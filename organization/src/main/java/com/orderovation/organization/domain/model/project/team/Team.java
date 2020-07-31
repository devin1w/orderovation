package com.orderovation.organization.domain.model.project.team;

import com.orderovation.organization.domain.model.member.Leader;
import org.springframework.util.StringUtils;

import javax.persistence.*;

/**
 * @author devin
 */
@Entity
@Table(name = "oo_team")
public class Team {
    @Id
    private String id;
    private String name;
    private String projectId;
    private TeamType teamType;
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "leader_id")),
            @AttributeOverride(name = "name", column = @Column(name = "leader_name"))
    })
    private Leader leader;

    public Team() {
    }

    public Team(String id, String name, String projectId, TeamType teamType, Leader leader) {
        this.setId(id);
        this.setName(name);
        this.setProjectId(projectId);
        this.setTeamType(teamType);
        this.setLeader(leader);
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        if (projectId == null) {
            throw new IllegalStateException("The projectId may not be set to null");
        }
        this.projectId = projectId;
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public void setTeamType(TeamType teamType) {
        if (teamType == null) {
            throw new IllegalStateException("The teamType may not be set to null");
        }
        this.teamType = teamType;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        if (leader == null) {
            throw new IllegalStateException("The leader may not be set to null");
        }
        this.leader = leader;
    }
}
