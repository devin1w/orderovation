package com.orderovation.order.domain.model.order.solution;

import com.orderovation.order.domain.model.order.solution.attachment.Attachment;
import com.orderovation.order.domain.model.participant.Processor;
import com.orderovation.order.domain.model.participant.Reviewer;

import java.util.List;

/**
 * @author devin
 */
public class Solution {
    private String id;
    private String orderId;
    private String description;
    private String review;
    private Processor processor;
    private Reviewer reviewer;
    private List<Attachment> attachments;
}
