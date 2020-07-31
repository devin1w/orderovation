package com.orderovation.order.infrastructure.service;

import com.orderovation.order.domain.model.participant.Initiator;
import com.orderovation.order.domain.model.participant.Processor;
import com.orderovation.order.domain.model.participant.Reviewer;
import com.orderovation.order.domain.service.ParticipantService;
import com.orderovation.order.infrastructure.exception.ContextAdapterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author devin
 */
@Service
public class TranslatingParticipantServiceImpl implements ParticipantService {

    @Autowired
    private OrganizationAdapter organizationAdapter;

    @Override
    public Initiator initiatorFrom(String initiatorId, String projectId) throws Exception {
        return this.organizationAdapter.toInitiator(projectId, initiatorId, "Initiator", Initiator.class);
    }

    @Override
    public Reviewer reviewerFrom(String projectId) throws Exception {
        return this.organizationAdapter.toReviewer(projectId, "Reviewer", Reviewer.class);
    }
}
