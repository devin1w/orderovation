package com.orderovation.organization.infrastructure;

import com.orderovation.organization.application.BusiException;
import com.orderovation.organization.domain.model.member.Admin;
import com.orderovation.organization.domain.model.member.Member;
import com.orderovation.organization.ui.dto.RspEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class IdentityAdapter {

    @Autowired
    private RestTemplate restTemplate;

    public <T extends Member> T toAdmin(String adminId, String admin, Class<T> adminClass)
            throws ContextAdapterException, BusiException {
        T member = null;
        try {
            Map<String, Object> rspMap = restTemplate.getForEntity("http://IDENTITY-SERVICE/admin/id/{1}",
                    Map.class,
                    adminId).getBody();

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

            member = new MemberTranslator().toMemberFromRepresentation(
                    rspMap.get("data"), adminClass
            );
        } catch (BusiException e) {
            throw e;
        } catch (Exception e) {
            throw new ContextAdapterException();
        }
        return member;
    }

    public <T extends Member> T toMember(String memberId, String memberName, Class<T> memberClass)
            throws ContextAdapterException, BusiException {
        T member = null;
        try {
            Map<String, Object> rspMap = restTemplate.getForEntity("http://IDENTITY-SERVICE/user/id/{1}",
                    Map.class,
                    memberId).getBody();

            if (rspMap == null) {
                throw new BusiException("用户[" + memberId + "]不存在");
            }

            member = new MemberTranslator().toMemberFromRepresentation(
                    rspMap, memberClass
            );
        } catch (BusiException e) {
            throw e;
        } catch (Exception e) {
            throw new ContextAdapterException();
        }
        return member;
    }
}
