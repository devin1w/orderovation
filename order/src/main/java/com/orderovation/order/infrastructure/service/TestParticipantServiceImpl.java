package com.orderovation.order.infrastructure.service;

import com.orderovation.order.domain.model.participant.Initiator;
import com.orderovation.order.domain.model.participant.Processor;
import com.orderovation.order.domain.model.participant.Reviewer;
import com.orderovation.order.domain.service.ParticipantService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * 测试用
 * @author devin
 */
//@Component
public class TestParticipantServiceImpl implements ParticipantService {
    @Override
    public Initiator initiatorFrom(String initiatorId, String projectId) {
        return new Initiator("1", "weisy", "18666667777", "weisy@123");
    }

    @Override
    public Reviewer reviewerFrom(String projectId) {
        return new Reviewer("123", "reviewer", "123", "123@123");
    }
}
