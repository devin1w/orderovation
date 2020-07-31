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

public class UserLikeNicknameSpecification implements Specification<User> {

    private String nickname;

    public UserLikeNicknameSpecification(String nickname) {
        if (StringUtils.isEmpty(nickname)) {
            throw new IllegalStateException("The nickname may not be set to null");
        }
        this.nickname = nickname;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> list = new ArrayList<>();
        if (nickname != null) {
            list.add(criteriaBuilder.like(root.get("nickname").as(String.class), "%" + nickname + "%"));
        }
        Predicate[] p = new Predicate[list.size()];
        return criteriaBuilder.and(list.toArray(p));
    }
}
