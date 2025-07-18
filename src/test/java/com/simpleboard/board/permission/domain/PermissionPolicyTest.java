package com.simpleboard.board.permission.domain;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.permission.domain.exception.AssignmentNotFoundException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PermissionPolicyTest {

  PermissionPolicy policy;

  @BeforeEach
  void setUp() {
    policy = new PermissionPolicy(1L);
  }

  @Test
  void assignRole_성공() {
    // given
    Long userId = 10L;

    // when
    policy.assignRole(userId, RoleName.POST_ADMIN);

    // then
    assertThat(policy.can(userId, Permission.CREATE_BOARD)).isTrue();
    assertThat(policy.can(userId, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void deleteAssignment_성공() {
    // given
    Long userId = 20L;
    policy.assignRole(userId, RoleName.POST_ADMIN);

    // when
    policy.deleteAssignment(userId);

    // then
    assertThatThrownBy(() -> policy.can(userId, Permission.CREATE_BOARD))
        .isInstanceOf(NoSuchElementException.class);
    assertThatThrownBy(() -> policy.can(userId, Permission.DELETE_BOARD))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void delegateRole_성공() {
    // given
    Long fromUser = 1L;
    Long toUser = 2L;
    policy.assignRole(fromUser, RoleName.POST_ADMIN);

    // when
    policy.delegateRole(fromUser, toUser, RoleName.POST_ADMIN);

    // then
    assertThatThrownBy(() -> policy.can(fromUser, Permission.CREATE_BOARD))
        .isInstanceOf(NoSuchElementException.class);
    assertThatThrownBy(() -> policy.can(fromUser, Permission.DELETE_BOARD))
        .isInstanceOf(NoSuchElementException.class);
    assertThat(policy.can(toUser, Permission.CREATE_BOARD)).isTrue();
    assertThat(policy.can(toUser, Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void delegateRole_실패_권한없음() {
    // given
    Long fromUser = 1L;
    Long toUser = 2L;
    policy.assignRole(fromUser, RoleName.POST_ADMIN);

    // when
    policy.deleteAssignment(fromUser);

    // then
    assertThatThrownBy(() -> policy.delegateRole(fromUser, toUser, RoleName.POST_ADMIN))
        .isInstanceOf(AssignmentNotFoundException.class);
  }
}
