package com.orderovation.order.application;

import com.orderovation.order.domain.model.order.Order;
import com.orderovation.order.domain.model.order.OrderRepository;
import com.orderovation.order.domain.model.order.transfer.TransferLog;
import com.orderovation.order.domain.model.order.transfer.TransferLogRepository;
import com.orderovation.order.domain.model.participant.Initiator;
import com.orderovation.order.domain.model.participant.Participant;
import com.orderovation.order.domain.model.participant.Reviewer;
import com.orderovation.order.domain.service.ParticipantService;
import com.orderovation.order.infrastructure.exception.ContextAdapterException;
import com.orderovation.order.ui.dto.SubmitOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author devin
 */
@Service
public class OrderApplicationService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TransferLogRepository transferLogRepository;

    @Autowired
    private ParticipantService participantService;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(rollbackFor = Throwable.class)
    public void initiate(SubmitOrderDTO submitOrderDTO) throws Exception {

        String initiatorId = submitOrderDTO.getInitiatorId();
        String projectId = submitOrderDTO.getProjectId();
        Integer orderType = submitOrderDTO.getOrderType();
        String title = submitOrderDTO.getTitle();
        String description = submitOrderDTO.getDescription();

        Initiator initiator = participantService.initiatorFrom(initiatorId, projectId);
        Order newOrder = new Order(projectId, orderType, title, description);
        initiator.initiate(newOrder);
        orderRepository.save(newOrder);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void initiationSubmit(Integer anOrderId) throws Exception {

        List<Order> orders = orderRepository.findAllById(Collections.singletonList(anOrderId));
        if (orders.size() == 0) {
            throw new IllegalArgumentException("The orders may not be set to null.");
        }
        Order order = orders.get(0);
        String projectId = order.getProjectId();

        Reviewer reviewer = participantService.reviewerFrom(projectId);
        order.submitTo(reviewer);
        orderRepository.save(order);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void createTransferLog(Integer anOrderId, String content, Participant sender, Participant receiver) {

        TransferLog transferLog = new TransferLog(anOrderId, content, sender, receiver);
        transferLogRepository.save(transferLog);
    }
}
