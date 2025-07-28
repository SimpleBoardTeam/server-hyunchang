package com.simpleboard.board.permission.domain;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.permission.domain.exception.AssignmentNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PermissionPolicyTest {

  private Long defaultUserId;
  private Long defaultBoardId;

  @BeforeEach
  void setUp() {
    defaultUserId = 1L;
    defaultBoardId = 1L;
  }

  @Test
  void create_성공() {
    // given
    Long boardId = 1L;
    Long userId = 1L;

    // when
    PermissionPolicy permissionPolicy = PermissionPolicy.create(boardId, userId);

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
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId);

    // then
    assertThat(permissionPolicy.can(defaultUserId, Permission.CREATE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(defaultUserId, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void can_실패_권한이_없는_유저가_실행() {
    // given
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId);
    Long notOwnPermissionUserId = 2L;

    // then
    assertThat(permissionPolicy.can(notOwnPermissionUserId, Permission.CREATE_BOARD)).isFalse();
    assertThat(permissionPolicy.can(notOwnPermissionUserId, Permission.DELETE_BOARD)).isFalse();
  }

  @Test
  void assignRole_성공() {
    // given
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId);
    Long otherUserId = 2L;

    // when
    permissionPolicy.assignRole(otherUserId, RoleName.BOARD_ADMIN);

    // then
    assertThat(permissionPolicy.can(otherUserId, Permission.CREATE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(otherUserId, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void deleteAssignment_성공() {
    // given
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId);
    Long otherUserId = 2L;
    permissionPolicy.assignRole(otherUserId, RoleName.BOARD_ADMIN);

    // when
    permissionPolicy.deleteAssignment(otherUserId);

    // then
    assertThat(permissionPolicy.can(otherUserId, Permission.CREATE_BOARD)).isFalse();
    assertThat(permissionPolicy.can(otherUserId, Permission.DELETE_BOARD)).isFalse();
  }

  @Test
  void delegateRole_성공() {
    // given
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId);
    Long fromUser = 2L;
    Long toUser = 3L;
    permissionPolicy.assignRole(fromUser, RoleName.BOARD_ADMIN);

    // when
    permissionPolicy.delegateRole(fromUser, toUser, RoleName.BOARD_ADMIN);

    // then
    assertThat(permissionPolicy.can(fromUser, Permission.CREATE_BOARD)).isFalse();
    assertThat(permissionPolicy.can(fromUser, Permission.DELETE_BOARD)).isFalse();

    assertThat(permissionPolicy.can(toUser, Permission.CREATE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(toUser, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void delegateRole_실패_권한이_없는_유저가_권한_위임() {
    // given
    PermissionPolicy permissionPolicy = PermissionPolicy.create(defaultBoardId, defaultUserId);
    Long fromUser = 2L;
    Long toUser = 3L;
    permissionPolicy.assignRole(fromUser, RoleName.BOARD_ADMIN);
    permissionPolicy.deleteAssignment(fromUser);

    // when & then
    assertThatThrownBy(() -> permissionPolicy.delegateRole(fromUser, toUser, RoleName.BOARD_ADMIN))
        .isInstanceOf(AssignmentNotFoundException.class);
  }
}
