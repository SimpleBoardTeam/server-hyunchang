package com.simpleboard.board.permission.infrastructure.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.simpleboard.board.permission.domain.ManagerAssignment;
import com.simpleboard.board.permission.domain.RoleName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ManagerAssignmentEntityTest {

  @DisplayName("도메인 → 엔티티 변환 테스트")
  @Test
  void fromDomainToEntity() {
    // given
    ManagerAssignment domain = ManagerAssignment.create(1L, 100L, RoleName.BOARD_ADMIN);

    // when
    ManagerAssignmentEntity entity = new ManagerAssignmentEntity(domain);

    // then
    assertThat(entity.getBoardId()).isEqualTo(1L);
    assertThat(entity.getUserId()).isEqualTo(100L);
    assertThat(entity.getRoleName()).isEqualTo(RoleName.BOARD_ADMIN);
  }

  @DisplayName("엔티티 → 도메인 변환 테스트")
  @Test
  void fromEntityToDomain() {
    // given
    ManagerAssignmentEntity entity = new ManagerAssignmentEntity(1L, 100L, RoleName.BOARD_ADMIN);

    // when
    ManagerAssignment domain = entity.toDomain();

    // then
    assertThat(domain.getBoardId()).isEqualTo(1L);
    assertThat(domain.getUserId()).isEqualTo(100L);
    assertThat(domain.getRoleName()).isEqualTo(RoleName.BOARD_ADMIN);
  }
}
