package com.simpleboard.board.permission.domain;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.permission.domain.vo.Permission;
import com.simpleboard.board.permission.domain.vo.Role;
import com.simpleboard.board.permission.domain.vo.RoleName;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleTest {

  Role role;

  @BeforeEach
  void setUp() {
    role = Role.of(RoleName.BOARD_ADMIN, Set.of(Permission.CREATE_BOARD, Permission.DELETE_BOARD));
  }

  @Test
  void of_정상적으로_권한_객체를_반환한다() {

    // then
    assertThat(role).isNotNull();
    assertThat(role.hasPermission(Permission.CREATE_BOARD)).isTrue();
    assertThat(role.hasPermission(Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void hasSameRole_역할이_같으면_true를_반환한다() {
    // given
    Role newRole = Role.of(RoleName.BOARD_ADMIN, Set.of(Permission.CREATE_BOARD, Permission.DELETE_BOARD));

    // when & then
    assertThat(role.isSameRole(newRole)).isTrue();
  }
}
