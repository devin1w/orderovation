package com.orderovation.order.domain.model.order.comment;

import com.orderovation.order.domain.model.participant.Participant;

import java.util.Date;

/**
 * @author devin
 */
public class Comment {
    private String id;
    private String commentId;
    private String content;
    private Date commentDate;
    private Participant participant;
}
