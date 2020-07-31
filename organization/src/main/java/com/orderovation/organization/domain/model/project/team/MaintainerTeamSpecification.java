package com.orderovation.organization.domain.model.project.team;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class MaintainerTeamSpecification implements Specification<Team> {

    private String projectId;

    public MaintainerTeamSpecification(String projectId) {
        if (projectId == null) {
            throw new IllegalStateException("The projectId may not be set to null");
        }
        this.projectId = projectId;
    }

    @Override
    public Predicate toPredicate(Root<Team> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> list = new ArrayList<>();
        if (projectId != null) {
            list.add(criteriaBuilder.equal(root.get("projectId").as(String.class), projectId));
            list.add(criteriaBuilder.equal(root.get("teamType").as(TeamType.class), TeamType.MAINTAINER));
        }
        Predicate[] p = new Predicate[list.size()];
        return criteriaBuilder.and(list.toArray(p));
    }
}
