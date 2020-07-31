package com.orderovation.organization.domain.service;

import com.orderovation.organization.domain.model.member.Admin;
import com.orderovation.organization.domain.model.member.Leader;
import com.orderovation.organization.domain.model.member.Manager;
import com.orderovation.organization.domain.model.member.Member;

public interface OrganizationService {

    String nextIdentity();

    Admin adminFrom(String adminId) throws Exception;

    Manager managerFrom(String managerId) throws Exception;

    Leader leaderFrom(String leaderId) throws Exception;

    Member memberFrom(String memberId) throws Exception;
}
