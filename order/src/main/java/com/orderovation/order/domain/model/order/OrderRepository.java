package com.orderovation.order.domain.model.order;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author devin
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

}