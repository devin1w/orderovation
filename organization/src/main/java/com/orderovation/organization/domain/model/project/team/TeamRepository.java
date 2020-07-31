package com.orderovation.organization.domain.model.project.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeamRepository extends JpaRepository<Team, String>, JpaSpecificationExecutor<Team> {
}
