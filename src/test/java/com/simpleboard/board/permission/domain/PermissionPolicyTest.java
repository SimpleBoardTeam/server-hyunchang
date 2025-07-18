package com.simpleboard.board.permission.domain;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.permission.domain.exception.AssignmentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PermissionPolicyTest {

  PermissionPolicy permissionPolicy;
  Long testUserId;

  @BeforeEach
  void setUp() {
    testUserId = 1L;
    permissionPolicy = PermissionPolicy.create(1L, testUserId);
  }

  @Test
  void creat_성공() {
    // given & when
    Long userId = 1L;
    PermissionPolicy policy = PermissionPolicy.create(1L, userId);

    // then
    assertThat(permissionPolicy.can(userId, Permission.CREATE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(userId, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void can_성공() {
    assertThat(permissionPolicy.can(testUserId, Permission.CREATE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(testUserId, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void can_실패_권한이_없는_유저가_실행() {
    Long notOwnPermissionUserId = 2L;
    assertThatThrownBy(() -> permissionPolicy.can(notOwnPermissionUserId, Permission.CREATE_BOARD))
        .isInstanceOf(AssignmentNotFoundException.class);
    assertThatThrownBy(() -> permissionPolicy.can(notOwnPermissionUserId, Permission.DELETE_BOARD))
        .isInstanceOf(AssignmentNotFoundException.class);
  }

  @Test
  void assignRole_성공() {
    // given
    Long userId = 2L;

    // when
    permissionPolicy.assignRole(userId, RoleName.BOARD_ADMIN);

    // then
    assertThat(permissionPolicy.can(userId, Permission.CREATE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(userId, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void deleteAssignment_성공() {
    // given
    Long userId = 2L;
    permissionPolicy.assignRole(userId, RoleName.BOARD_ADMIN);

    // when
    permissionPolicy.deleteAssignment(userId);

    // then
    assertThatThrownBy(() -> permissionPolicy.can(userId, Permission.CREATE_BOARD))
        .isInstanceOf(AssignmentNotFoundException.class);
    assertThatThrownBy(() -> permissionPolicy.can(userId, Permission.DELETE_BOARD))
        .isInstanceOf(AssignmentNotFoundException.class);
  }

  @Test
  void delegateRole_성공() {
    // given
    Long fromUser = 2L;
    Long toUser = 3L;
    permissionPolicy.assignRole(fromUser, RoleName.BOARD_ADMIN);

    // when
    permissionPolicy.delegateRole(fromUser, toUser, RoleName.BOARD_ADMIN);

    // then
    assertThatThrownBy(() -> permissionPolicy.can(fromUser, Permission.CREATE_BOARD))
        .isInstanceOf(AssignmentNotFoundException.class);
    assertThatThrownBy(() -> permissionPolicy.can(fromUser, Permission.DELETE_BOARD))
        .isInstanceOf(AssignmentNotFoundException.class);
    assertThat(permissionPolicy.can(toUser, Permission.CREATE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(toUser, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void delegateRole_실패_권한이_없는_유저가_권한_위임용() {
    // given
    Long fromUser = 2L;
    Long toUser = 3L;
    permissionPolicy.assignRole(fromUser, RoleName.BOARD_ADMIN);

    // when
    permissionPolicy.deleteAssignment(fromUser);

    // then
    assertThatThrownBy(() -> permissionPolicy.delegateRole(fromUser, toUser, RoleName.BOARD_ADMIN))
        .isInstanceOf(AssignmentNotFoundException.class);
  }
}
