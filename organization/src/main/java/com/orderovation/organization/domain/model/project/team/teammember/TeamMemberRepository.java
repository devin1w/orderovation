package com.orderovation.organization.domain.model.project.team.teammember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeamMemberRepository extends JpaRepository<TeamMember, String>, JpaSpecificationExecutor<TeamMember> {
}
