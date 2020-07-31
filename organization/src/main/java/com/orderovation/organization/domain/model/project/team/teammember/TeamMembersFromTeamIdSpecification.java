package com.orderovation.organization.domain.model.project.team.teammember;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TeamMembersFromTeamIdSpecification implements Specification<TeamMember> {

    private String teamId;

    public TeamMembersFromTeamIdSpecification(String teamId) {
        if (teamId == null) {
            throw new IllegalStateException("The teamId may not be set to null");
        }
        this.teamId = teamId;
    }

    @Override
    public Predicate toPredicate(Root<TeamMember> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> list = new ArrayList<>();
        if (teamId != null) {
            list.add(criteriaBuilder.equal(root.get("teamId").as(String.class), teamId));
        }
        Predicate[] p = new Predicate[list.size()];
        return criteriaBuilder.and(list.toArray(p));
    }
}
