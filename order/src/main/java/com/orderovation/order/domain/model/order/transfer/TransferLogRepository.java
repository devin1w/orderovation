package com.orderovation.order.domain.model.order.transfer;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author devin
 */
public interface TransferLogRepository extends JpaRepository<TransferLog, Integer> {
}
