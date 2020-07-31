package com.devin.seckill.domain.model.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author devin
 */
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {
}
