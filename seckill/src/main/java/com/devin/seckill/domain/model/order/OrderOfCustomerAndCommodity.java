package com.devin.seckill.domain.model.order;

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

public class OrderOfCustomerAndCommodity implements Specification<Order> {

    private String customerId;
    private String commodityId;

    public void setCustomerId(String customerId) {
        if (StringUtils.isEmpty(customerId)) {
            throw new IllegalStateException("The customerId may not be set to null");
        }
        this.customerId = customerId;
    }

    public void setCommodityId(String commodityId) {
        if (StringUtils.isEmpty(commodityId)) {
            throw new IllegalStateException("The commodityId may not be set to null");
        }
        this.commodityId = commodityId;
    }

    public OrderOfCustomerAndCommodity(String customerId, String commodityId) {
        this.setCustomerId(customerId);
        this.setCommodityId(commodityId);
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> list = new ArrayList<>();
        list.add(criteriaBuilder.equal(root.get("customerId").as(String.class), customerId));
        list.add(criteriaBuilder.equal(root.get("commodityId").as(String.class), commodityId));
        Predicate[] p = new Predicate[list.size()];
        return criteriaBuilder.and(list.toArray(p));
    }
}
