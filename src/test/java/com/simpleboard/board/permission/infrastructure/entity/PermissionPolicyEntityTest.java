package com.simpleboard.board.permission.infrastructure.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.simpleboard.board.permission.domain.entity.ManagerAssignment;
import com.simpleboard.board.permission.domain.entity.PermissionPolicy;
import com.simpleboard.board.permission.domain.vo.Permission;
import com.simpleboard.board.permission.domain.vo.Role;
import com.simpleboard.board.permission.domain.vo.RoleName;
import com.simpleboard.board.permission.infrastructure.mapper.ManagerAssignmentMapper;
import com.simpleboard.board.permission.infrastructure.mapper.PermissionPolicyMapper;
import com.simpleboard.board.permission.infrastructure.repository.InMemoryRoleCatalog;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PermissionPolicyEntityTest {

  PermissionPolicyMapper permissionPolicyMapper;

  @BeforeEach
  void setUp() {
    ManagerAssignmentMapper managerAssignmentMapper = new ManagerAssignmentMapper(new InMemoryRoleCatalog());
    permissionPolicyMapper = new PermissionPolicyMapper(managerAssignmentMapper);
  }

  @DisplayName("도메인 → 엔티티 변환 테스트")
  @Test
  void fromDomainToEntity() {
    // given
    PermissionPolicy domain = PermissionPolicy.create(1L, 100L, Role.of(RoleName.BOARD_ADMIN, Set.of(
        Permission.CREATE_BOARD, Permission.DELETE_BOARD)));

    // when
    PermissionPolicyEntity entity = permissionPolicyMapper.toEntity(domain);

    // then
    assertThat(entity.getBoardId()).isEqualTo(1L);
    assertThat(entity.getManagerAssignments()).hasSize(1);
    ManagerAssignmentEntity assignment = entity.getManagerAssignments().get(0);
    assertThat(assignment.getBoardId()).isEqualTo(1L);
    assertThat(assignment.getUserId()).isEqualTo(100L);
    assertThat(assignment.getRoleName()).isEqualTo(RoleName.BOARD_ADMIN);
  }

  @DisplayName("엔티티 → 도메인 변환 테스트")
  @Test
  void fromEntityToDomain() {
    // given
    ManagerAssignmentEntity assignmentEntity =
        new ManagerAssignmentEntity(1L, 100L, RoleName.BOARD_ADMIN);
    PermissionPolicyEntity entity = new PermissionPolicyEntity(1L, List.of(assignmentEntity));

    // when
    PermissionPolicy domain = permissionPolicyMapper.toDomain(entity);

    // then
    assertThat(domain.getBoardId()).isEqualTo(1L);
    assertThat(domain.getManagerAssignments()).hasSize(1);
    ManagerAssignment assignment = domain.getManagerAssignments().get(0);
    assertThat(assignment.getBoardId()).isEqualTo(1L);
    assertThat(assignment.getUserId()).isEqualTo(100L);
    assertThat(assignment.getRoleName()).isEqualTo(RoleName.BOARD_ADMIN);
  }
}
