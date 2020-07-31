package com.orderovation.order.infrastructure.service;

import com.orderovation.order.domain.model.participant.Participant;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author devin
 */
public class ParticipantTranslator {
    public <T extends Participant> T toParticipantFromRepresentation(Object entity, Class<T> participantClass) {
        Map<String, Object> member = (Map<String, Object>) entity;

        String id = (String) member.get("id");
        String name = (String) member.get("name");
        String serialNumber = (String) member.get("serialNumber");
        String email = (String) member.get("email");

        T participant = null;
        try {
            participant = this.newParticipant(id, name, serialNumber, email, participantClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return participant;
    }

    private <T extends Participant> T newParticipant(String id, String name, String serialNumber, String email, Class<T> participantClass) throws NoSuchMethodException {
        Constructor<T> constructor = participantClass.getConstructor(
                String.class, String.class, String.class, String.class
        );
        T participant = null;
        try {
            participant = constructor.newInstance(id, name, serialNumber, email);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return participant;
    }
}
