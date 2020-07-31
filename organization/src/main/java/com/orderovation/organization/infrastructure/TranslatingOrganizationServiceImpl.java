package com.orderovation.organization.infrastructure;

import com.orderovation.organization.application.BusiException;
import com.orderovation.organization.domain.model.member.Admin;
import com.orderovation.organization.domain.model.member.Leader;
import com.orderovation.organization.domain.model.member.Manager;
import com.orderovation.organization.domain.model.member.Member;
import com.orderovation.organization.domain.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author devin
 */
@Service
public class TranslatingOrganizationServiceImpl implements OrganizationService {

    @Autowired
    private IdentityAdapter identityAdapter;

    @Override
    public String nextIdentity() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Admin adminFrom(String adminId) throws Exception {
        return this.identityAdapter.toAdmin(adminId, "Admin", Admin.class);
    }

    @Override
    public Manager managerFrom(String managerId) throws Exception {
        return this.identityAdapter.toMember(managerId, "Manager", Manager.class);
    }

    @Override
    public Leader leaderFrom(String leaderId) throws Exception {
        return this.identityAdapter.toMember(leaderId, "Leader", Leader.class);
    }

    @Override
    public Member memberFrom(String memberId) throws Exception {
        return this.identityAdapter.toMember(memberId, "Member", Member.class);
    }
}
