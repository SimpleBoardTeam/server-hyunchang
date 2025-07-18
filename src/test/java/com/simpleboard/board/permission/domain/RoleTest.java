package com.simpleboard.board.permission.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RoleTest {

  @Test
  void of_정상적으로_권한_객체를_반환한다() {
    // given
    Role role = Role.of(RoleName.BOARD_ADMIN);

    // then
    assertThat(role).isNotNull();
    assertThat(role.hasPermission(Permission.CREATE_BOARD)).isTrue();
    assertThat(role.hasPermission(Permission.DELETE_BOARD)).isTrue();
  }

  @Test
  void hasSameRole_역할이_같으면_true를_반환한다() {
    // given
    Role role = Role.of(RoleName.BOARD_ADMIN);

    // when & then
    assertThat(role.hasSameRole(RoleName.BOARD_ADMIN)).isTrue();
  }
}
