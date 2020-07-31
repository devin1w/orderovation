package com.orderovation.organization.infrastructure;

import com.orderovation.organization.domain.model.member.Member;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class MemberTranslator {
    public <T extends Member> T toMemberFromRepresentation(Object data, Class<T> memberClass) {
        Map<String, Object> userMap = (Map<String, Object>) data;

        String id = (String) userMap.get("userId");
        String name = (String) userMap.get("nickname");
        String serialNumber = (String) userMap.get("serialNumber");
        String email = (String) userMap.get("email");

        T member = null;
        try {
            member = this.newMember(id, name, serialNumber, email, memberClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return member;
    }

    private <T extends Member> T newMember(String id, String name, String serialNumber, String email, Class<T> memberClass) throws NoSuchMethodException {
        Constructor<T> constructor = memberClass.getConstructor(
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
