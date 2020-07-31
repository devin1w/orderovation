package com.orderovation.identityaccess.domain.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserWithUsernameSpecification implements Specification<User> {

    private String username;

    public UserWithUsernameSpecification(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new IllegalStateException("The username may not be set to null");
        }
        this.username = username;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> list = new ArrayList<>();
        if (username != null) {
            list.add(criteriaBuilder.equal(root.get("username").as(String.class), username));
        }
        Predicate[] p = new Predicate[list.size()];
        return criteriaBuilder.and(list.toArray(p));
    }
}
