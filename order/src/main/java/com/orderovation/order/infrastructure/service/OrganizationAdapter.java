package com.orderovation.order.infrastructure.service;

import com.orderovation.order.domain.model.participant.Participant;
import com.orderovation.order.infrastructure.exception.ContextAdapterException;
import com.orderovation.order.ui.dto.RspEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OrganizationAdapter {

    @Autowired
    private RestTemplate restTemplate;

    public <T extends Participant> T toInitiator(String projectId, String participantId, String participantName, Class<T> participantClass)
            throws ContextAdapterException, BusiException {
        T participant = null;
        try {
            Map<String, Object> rspMap = restTemplate.getForEntity("http://ORGANIZATION-SERVICE/operator/project/{1}/id/{2}",
                    Map.class,
                    projectId, participantId).getBody();

            if (rspMap == null) {
                throw new IllegalStateException();
            }
            String status = (String) rspMap.get("status");
            String msg = (String) rspMap.get("msg");
            if (RspEnum.BUSI_ERROR.getCode().equals(status)) {
                throw new BusiException(msg);
            }
            if (!RspEnum.SUCCESS.getCode().equals(status)) {
                throw new IllegalStateException();
            }

            participant = new ParticipantTranslator().toParticipantFromRepresentation(
                    rspMap.get("data"), participantClass
            );
        } catch (BusiException e) {
            throw e;
        } catch (Exception e) {
            throw new ContextAdapterException();
        }
        return participant;
    }

    public <T extends Participant> T toReviewer(String projectId, String participantName, Class<T> participantClass)
            throws ContextAdapterException, BusiException {
        T participant = null;
        try {
            Map<String, Object> rspMap = restTemplate.getForEntity("http://ORGANIZATION-SERVICE/maintainer/leader/project/{1}",
                    Map.class,
                    projectId).getBody();

            if (rspMap == null) {
                throw new IllegalStateException();
            }
            String status = (String) rspMap.get("status");
            String msg = (String) rspMap.get("msg");
            if (RspEnum.BUSI_ERROR.getCode().equals(status)) {
                throw new BusiException(msg);
            }
            if (!RspEnum.SUCCESS.getCode().equals(status)) {
                throw new IllegalStateException();
            }

            participant = new ParticipantTranslator().toParticipantFromRepresentation(
                    rspMap.get("data"), participantClass
            );
        } catch (BusiException e) {
            throw e;
        } catch (Exception e) {
            throw new ContextAdapterException();
        }
        return participant;
    }
}
