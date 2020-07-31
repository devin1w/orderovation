package com.orderovation.organization.application;

import com.orderovation.organization.domain.model.member.*;
import com.orderovation.organization.domain.model.project.Project;
import com.orderovation.organization.domain.model.project.ProjectRepository;
import com.orderovation.organization.domain.model.project.team.*;
import com.orderovation.organization.domain.model.project.team.teammember.TeamMember;
import com.orderovation.organization.domain.model.project.team.teammember.TeamMembersFromTeamIdSpecification;
import com.orderovation.organization.domain.model.project.team.teammember.TeamMemberRepository;
import com.orderovation.organization.domain.service.OrganizationService;
import com.orderovation.organization.ui.dto.MemberFormDTO;
import com.orderovation.organization.ui.dto.ProjectFormDTO;
import com.orderovation.organization.ui.dto.TeamFormDTO;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author devin
 */
@Service
public class OrganizationApplicationService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private OrganizationService organizationService;

    @Transactional(rollbackFor = Throwable.class)
    public Operator findOperator(String projectId, String operatorId) throws Exception {

        List<Team> operatorTeamList = teamRepository.findAll(new OperatorTeamSpecification(projectId));
        if (operatorTeamList.size() == 0) {
            throw new BusiException("项目[" + projectId + "]无运营团队");
        }
        Team operatorTeam = operatorTeamList.get(0);
        String teamId = operatorTeam.getId();
        List<TeamMember> teamMembers = teamMemberRepository.findAll(new TeamMembersFromTeamIdSpecification(teamId));
        if (teamMembers.size() == 0) {
            throw new BusiException("项目[" + projectId + "]无成员[" + operatorId + "]");
        }
        List<TeamMember> matched = teamMembers.stream().filter(teamMember -> teamMember.getMember().getId().equals(operatorId)).collect(Collectors.toList());
        if (matched.size() == 0) {
            throw new BusiException("项目[" + projectId + "]无成员[" + operatorId + "]");
        }
        return new Operator(matched.get(0).getMember());
    }

    @Transactional(rollbackFor = Throwable.class)
    public Leader findMaintainerLeader(String projectId) throws Exception {

        List<Team> maintainerTeamList = teamRepository.findAll(new MaintainerTeamSpecification(projectId));
        if (maintainerTeamList.size() == 0) {
            throw new BusiException("项目[" + projectId + "]无运维团队");
        }
        Team maintainerTeam = maintainerTeamList.get(0);
        return maintainerTeam.getLeader();
    }

    @Transactional(rollbackFor = Throwable.class)
    public List<Project> findAllProject() {
        return projectRepository.findAll();
    }

    @Transactional(rollbackFor = Throwable.class)
    public void addOrUpdateProject(ProjectFormDTO projectFormDTO) throws Exception {
        String projectId = projectFormDTO.getId();
        String adminId = projectFormDTO.getAdminId();
        String name = projectFormDTO.getName();
        String managerId = projectFormDTO.getManagerId();

        Admin admin = organizationService.adminFrom(adminId);
        if (admin == null) {
            throw new BusiException("管理员[" + adminId + "]不存在");
        }
        Manager manager = organizationService.managerFrom(managerId);
        if (StringUtils.isEmpty(projectId)) {
            String newProjectId = organizationService.nextIdentity();
            Project project = admin.createProject(newProjectId, name, manager);
            projectRepository.save(project);
        } else {
            Project project = projectRepository.findById(projectId).orElseThrow(() -> new BusiException("项目[" + projectId + "]不存在"));
            if (!StringUtils.isEmpty(name)) {
                admin.changeProjectName(project, name);
            }
            if (manager != null) {
                admin.changeProjectManager(project, manager);
            }
            projectRepository.save(project);
        }
    }

    public void deleteProject(String projectId) {
        projectRepository.deleteById(projectId);
    }

    public List<Team> findAllTeam() {
        return teamRepository.findAll();
    }

    public List<TeamMember> findAllMember() {
        return teamMemberRepository.findAll();
    }

    public void addOrUpdateTeam(TeamFormDTO teamFormDTO) throws Exception {
        String projectId = teamFormDTO.getProjectId();
        String adminId = teamFormDTO.getAdminId();
        String name = teamFormDTO.getName();
        String leaderId = teamFormDTO.getLeaderId();
        String teamId = teamFormDTO.getId();
        String teamType = teamFormDTO.getTeamType();

        Admin admin = organizationService.adminFrom(adminId);
        if (admin == null) {
            throw new BusiException("管理员[" + adminId + "]不存在");
        }
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new BusiException("项目[" + projectId + "]不存在"));
        Manager manager = project.getManager();
        Leader leader = organizationService.leaderFrom(leaderId);
        if (StringUtils.isEmpty(teamId)) {
            // 新增团队
            String newTeamId = organizationService.nextIdentity();
            Team team = manager.createTeam(newTeamId, name, projectId, TeamType.valueOfCode(teamType), leader);
            teamRepository.save(team);
        } else {
            // 修改团队
            Team team = teamRepository.findById(teamId).orElseThrow(() -> new BusiException("团队[" + teamId + "]不存在"));
            if (!team.getProjectId().equals(projectId)) {
                boolean projectExists = projectRepository.existsById(projectId);
                if (projectExists) {
                    admin.moveTeamToProject(team, projectId);
                }
            }
            if (!StringUtils.isEmpty(name)) {
                manager.changeTeamName(team, name);
            }
            if (!StringUtils.isEmpty(teamType)) {
                manager.changeTeamType(team, TeamType.valueOfCode(teamType));
            }
            if (leader != null) {
                manager.changeTeamLeader(team, leader);
            }
            teamRepository.save(team);
        }
    }

    public List<Team> findTeamOfProjectId(String projectId) {
        return teamRepository.findAll(new TeamOfProjectSpecification(projectId));
    }

    public void addMember(MemberFormDTO memberFormDTO) throws Exception {
        String projectId = memberFormDTO.getProjectId();
        String adminId = memberFormDTO.getAdminId();
        String teamId = memberFormDTO.getTeamId();
        String memberId = memberFormDTO.getId();

        Admin admin = organizationService.adminFrom(adminId);
        if (admin == null) {
            throw new BusiException("管理员[" + adminId + "]不存在");
        }
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new BusiException("项目[" + projectId + "]不存在"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new BusiException("团队[" + teamId + "]不存在"));
        Leader leader = team.getLeader();
        Member member = organizationService.memberFrom(memberId);
        String teamMemberId = organizationService.nextIdentity();
        if (member == null) {
            throw new BusiException("用户[" + memberId + "]不存在");
        }
        TeamMember teamMember = leader.addMember(teamMemberId, teamId, member);
        teamMemberRepository.save(teamMember);
    }

    public void removeMember(String memberId) throws Exception {
        teamMemberRepository.deleteById(memberId);
    }

    public void deleteTeam(String teamId) {
        teamRepository.deleteById(teamId);
    }
}
