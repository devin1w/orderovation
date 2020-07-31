package com.orderovation.organization.ui;

import com.orderovation.organization.application.OrganizationApplicationService;
import com.orderovation.organization.domain.model.member.Leader;
import com.orderovation.organization.domain.model.member.Member;
import com.orderovation.organization.domain.model.member.Operator;
import com.orderovation.organization.domain.model.project.Project;
import com.orderovation.organization.domain.model.project.team.Team;
import com.orderovation.organization.domain.model.project.team.TeamType;
import com.orderovation.organization.domain.model.project.team.teammember.TeamMember;
import com.orderovation.organization.infrastructure.util.BeanUtil;
import com.orderovation.organization.ui.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author devin
 */
@RestController
public class OrganizationController {

    @Autowired
    private OrganizationApplicationService organizationApplicationService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello! -- Organization-Service";
    }

    @RequestMapping(value = "/operator/project/{projectId}/id/{operatorId}", method = RequestMethod.GET)
    public Rsp findOperator(@PathVariable String projectId, @PathVariable String operatorId) throws Exception {
        Operator operator = organizationApplicationService.findOperator(projectId, operatorId);
        return Rsp.success(operator);
    }

    @RequestMapping(value = "/maintainer/leader/project/{projectId}", method = RequestMethod.GET)
    public Rsp findMaintainerLeader(@PathVariable String projectId) throws Exception {
        Leader leader = organizationApplicationService.findMaintainerLeader(projectId);
        return Rsp.success(leader);
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public Rsp findProject() {
        List<Project> projects = organizationApplicationService.findAllProject();
        List<ProjectViewDTO> projectViewList = new ArrayList<>();
        projects.forEach(project -> {
            ProjectViewDTO projectViewDTO = BeanUtil.copyObject(project, ProjectViewDTO.class);
            projectViewDTO.setManagerId(project.getManager().getId());
            projectViewDTO.setManagerName(project.getManager().getName());
            projectViewDTO.setSerialNumber(project.getManager().getSerialNumber());
            projectViewDTO.setEmail(project.getManager().getEmail());
            projectViewList.add(projectViewDTO);
        });
        return Rsp.success(projectViewList);
    }

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public Rsp addOrUpdateProject(@RequestBody ProjectFormDTO projectFormDTO) throws Exception {
        organizationApplicationService.addOrUpdateProject(projectFormDTO);
        return findProject();
    }

    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.DELETE)
    public Rsp deleteProject(@PathVariable String projectId) throws Exception {
        organizationApplicationService.deleteProject(projectId);
        return findProject();
    }

    @RequestMapping(value = "/team", method = RequestMethod.GET)
    public Rsp findTeam() {
        List<Team> teams = organizationApplicationService.findAllTeam();
        List<TeamViewDTO> teamViewList = new ArrayList<>();
        teams.forEach(team -> {
            TeamViewDTO teamViewDTO = new TeamViewDTO();
            teamViewDTO.setProjectId(team.getProjectId());
            teamViewDTO.setProjectName("");
            teamViewDTO.setId(team.getId());
            teamViewDTO.setName(team.getName());
            teamViewDTO.setTeamType(team.getTeamType().getCode());
            teamViewDTO.setTeamTypeName(team.getTeamType().getName());
            teamViewDTO.setLeaderId(team.getLeader().getId());
            teamViewDTO.setLeaderName(team.getLeader().getName());
            teamViewList.add(teamViewDTO);
        });
        return Rsp.success(teamViewList);
    }

    @RequestMapping(value = "/team/projectId/{projectId}", method = RequestMethod.GET)
    public Rsp findTeamOfProjectId(@PathVariable String projectId) {
        List<Team> teams = organizationApplicationService.findTeamOfProjectId(projectId);
        List<TeamViewDTO> teamViewList = new ArrayList<>();
        teams.forEach(team -> {
            TeamViewDTO teamViewDTO = new TeamViewDTO();
            teamViewDTO.setProjectId(team.getProjectId());
            teamViewDTO.setProjectName("");
            teamViewDTO.setId(team.getId());
            teamViewDTO.setName(team.getName());
            teamViewDTO.setTeamType(team.getTeamType().getCode());
            teamViewDTO.setTeamTypeName(team.getTeamType().getName());
            teamViewDTO.setLeaderId(team.getLeader().getId());
            teamViewDTO.setLeaderName(team.getLeader().getName());
            teamViewList.add(teamViewDTO);
        });
        return Rsp.success(teamViewList);
    }

    @RequestMapping(value = "/team", method = RequestMethod.POST)
    public Rsp addOrUpdateTeam(@RequestBody TeamFormDTO teamFormDTO) throws Exception {
        organizationApplicationService.addOrUpdateTeam(teamFormDTO);
        return findTeam();
    }

    @RequestMapping(value = "/team/id/{teamId}", method = RequestMethod.DELETE)
    public Rsp deleteTeam(@PathVariable String teamId) throws Exception {
        organizationApplicationService.deleteTeam(teamId);
        return findTeam();
    }

    @RequestMapping(value = "/member", method = RequestMethod.GET)
    public Rsp findMember() {
        List<TeamMember> teamMembers = organizationApplicationService.findAllMember();
        List<TeamMemberViewDTO> teamMemberViewList = new ArrayList<>();
        teamMembers.forEach(teamMember -> {
            TeamMemberViewDTO teamMemberViewDTO = new TeamMemberViewDTO();
            teamMemberViewDTO.setId(teamMember.getId());
            teamMemberViewDTO.setTeamId(teamMember.getTeamId());
            teamMemberViewDTO.setTeamName(teamMember.getTeamId());
            teamMemberViewDTO.setMemberId(teamMember.getMember().getId());
            teamMemberViewDTO.setMemberName(teamMember.getMember().getName());
            teamMemberViewList.add(teamMemberViewDTO);
        });
        return Rsp.success(teamMemberViewList);
    }

    @RequestMapping(value = "/member/id/{memberId}", method = RequestMethod.DELETE)
    public Rsp deleteMember(@PathVariable String memberId) throws Exception {
        organizationApplicationService.removeMember(memberId);
        return findMember();
    }

    @RequestMapping(value = "/member", method = RequestMethod.POST)
    public Rsp addMember(@RequestBody MemberFormDTO memberFormDTO) throws Exception {
        organizationApplicationService.addMember(memberFormDTO);
        return findMember();
    }

    @RequestMapping(value = "/teamType", method = RequestMethod.GET)
    public Rsp findTeamType() {
        List<TeamType> teamTypeList = Arrays.asList(TeamType.values());
        List<TeamTypeViewDTO> teamTypeViewList = new ArrayList<>();
        teamTypeList.forEach(teamType -> {
            TeamTypeViewDTO teamTypeViewDTO = new TeamTypeViewDTO();
            teamTypeViewDTO.setCode(teamType.getCode());
            teamTypeViewDTO.setName(teamType.getName());
            teamTypeViewList.add(teamTypeViewDTO);
        });
        return Rsp.success(teamTypeViewList);
    }
}
