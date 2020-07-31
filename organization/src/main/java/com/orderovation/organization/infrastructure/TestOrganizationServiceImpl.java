package com.orderovation.organization.infrastructure;

import com.orderovation.organization.domain.model.member.Admin;
import com.orderovation.organization.domain.model.member.Leader;
import com.orderovation.organization.domain.model.member.Manager;
import com.orderovation.organization.domain.model.member.Member;
import com.orderovation.organization.domain.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

//@Service
public class TestOrganizationServiceImpl implements OrganizationService {

    @Override
    public String nextIdentity() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Admin adminFrom(String adminId) {
        if (!"1".equals(adminId)) {
            return null;
        }
        return null;
    }

    @Override
    public Manager managerFrom(String managerId) {
        return new Manager(managerId, "内存里的项目经理", "000", "000");
    }

    @Override
    public Leader leaderFrom(String leaderId) {
        return new Leader(new Member(leaderId, "内存里的团队负责人", "111", "111"));
    }

    @Override
    public Member memberFrom(String memberId) {
        return new Member(memberId, "内存里的成员", "222", "222");
    }
}
