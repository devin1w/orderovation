package com.orderovation.order.domain.service;

import com.orderovation.order.domain.model.participant.Initiator;
import com.orderovation.order.domain.model.participant.Processor;
import com.orderovation.order.domain.model.participant.Reviewer;
import com.orderovation.order.infrastructure.exception.ContextAdapterException;
import com.orderovation.order.infrastructure.service.BusiException;

/**
 * @author devin
 */
public interface ParticipantService {
    /**
     * 工单发起者工厂方法
     * @param initiatorId initiatorId
     * @param projectId projectId
     * @return Initiator
     */
    Initiator initiatorFrom(String initiatorId, String projectId) throws Exception;

    Reviewer reviewerFrom(String projectId) throws Exception;
}
