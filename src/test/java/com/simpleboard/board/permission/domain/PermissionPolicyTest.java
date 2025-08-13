package com.simpleboard.board.permission.domain;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.permission.domain.entity.ManagerAssignment;
import com.simpleboard.board.permission.domain.entity.PermissionPolicy;
import com.simpleboard.board.permission.domain.exception.AssignmentNotFoundException;
import com.simpleboard.board.permission.domain.repository.RoleCatalog;
import com.simpleboard.board.permission.domain.vo.Permission;
import com.simpleboard.board.permission.domain.vo.Role;
import com.simpleboard.board.permission.domain.vo.RoleName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PermissionPolicyTest {

  private Long defaultUserId;
  private Long defaultBoardId;
  Role adminRole;

  @BeforeEach
  void setUp() {
    defaultUserId = 1L;
    defaultBoardId = 1L;
    adminRole = Role.of(RoleName.BOARD_ADMIN, Set.of(Permission.CREATE_BOARD, Permission.DELETE_BOARD));
  }

  @Test
  void create_성공() {
    // given
    Long boardId = 1L;
    Long userId = 1L;

    // when
    PermissionPolicy permissionPolicy = PermissionPolicy.create(boardId, userId, adminRole);

    // then
    // 1. boardId 검증
    assertThat(permissionPolicy.getBoardId()).isEqualTo(boardId);

    // 2. Role 할당이 1개인지 검증
    List<ManagerAssignment> assignments = permissionPolicy.getManagerAssignments();
    assertThat(assignments).hasSize(1);
  }

  @Test
  void can_성공() {
    // given
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId, adminRole);

    // then
    assertThat(permissionPolicy.can(defaultUserId, Permission.CREATE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(defaultUserId, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void can_실패_권한이_없는_유저가_실행() {
    // given
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId, adminRole);
    Long notOwnPermissionUserId = 2L;

    // then
    assertThat(permissionPolicy.can(notOwnPermissionUserId, Permission.CREATE_BOARD)).isFalse();
    assertThat(permissionPolicy.can(notOwnPermissionUserId, Permission.DELETE_BOARD)).isFalse();
  }

  @Test
  void assignRole_성공() {
    // given
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId, adminRole);
    Long otherUserId = 2L;

    // when
    permissionPolicy.assignRole(otherUserId, adminRole);

    // then
    assertThat(permissionPolicy.can(otherUserId, Permission.CREATE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(otherUserId, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void deleteAssignment_성공() {
    // given
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId, adminRole);
    Long otherUserId = 2L;
    permissionPolicy.assignRole(otherUserId, adminRole);

    // when
    permissionPolicy.deleteAssignment(otherUserId);

    // then
    assertThat(permissionPolicy.can(otherUserId, Permission.CREATE_BOARD)).isFalse();
    assertThat(permissionPolicy.can(otherUserId, Permission.DELETE_BOARD)).isFalse();
  }

  @Test
  void delegateRole_성공() {
    // given
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId, adminRole);
    Long fromUser = 2L;
    Long toUser = 3L;
    permissionPolicy.assignRole(fromUser, adminRole);

    // when
    permissionPolicy.delegateRole(fromUser, toUser, adminRole);

    // then
    assertThat(permissionPolicy.can(fromUser, Permission.CREATE_BOARD)).isFalse();
    assertThat(permissionPolicy.can(fromUser, Permission.DELETE_BOARD)).isFalse();

    assertThat(permissionPolicy.can(toUser, Permission.CREATE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(toUser, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void delegateRole_실패_권한이_없는_유저가_권한_위임() {
    // given
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId, adminRole);
    Long fromUser = 2L;
    Long toUser = 3L;
    permissionPolicy.assignRole(fromUser, adminRole);
    permissionPolicy.deleteAssignment(fromUser);

    // when & then
    assertThatThrownBy(() -> permissionPolicy.delegateRole(fromUser, toUser, adminRole))
        .isInstanceOf(AssignmentNotFoundException.class);
  }
}
