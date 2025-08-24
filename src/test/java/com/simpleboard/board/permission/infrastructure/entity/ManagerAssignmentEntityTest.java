package com.simpleboard.board.permission.infrastructure.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.simpleboard.board.permission.domain.entity.ManagerAssignment;
import com.simpleboard.board.permission.domain.vo.Permission;
import com.simpleboard.board.permission.domain.vo.Role;
import com.simpleboard.board.permission.domain.vo.RoleName;
import com.simpleboard.board.permission.infrastructure.mapper.ManagerAssignmentMapper;
import com.simpleboard.board.permission.infrastructure.repository.InMemoryRoleCatalog;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ManagerAssignmentEntityTest {

  ManagerAssignmentMapper managerAssignmentMapper;

  @BeforeEach
  void setUp() {
    managerAssignmentMapper = new ManagerAssignmentMapper(new InMemoryRoleCatalog());
  }

  @DisplayName("도메인 → 엔티티 변환 테스트")
  @Test
  void fromDomainToEntity() {
    // given
    ManagerAssignment domain =
        ManagerAssignment.create(
            1L,
            100L,
            Role.of(
                RoleName.BOARD_ADMIN, Set.of(Permission.CREATE_BOARD, Permission.DELETE_BOARD)));

    // when
    ManagerAssignmentEntity entity = managerAssignmentMapper.toEntity(domain);

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
    ManagerAssignment domain = managerAssignmentMapper.toDomain(entity);

    // then
    assertThat(domain.getBoardId()).isEqualTo(1L);
    assertThat(domain.getUserId()).isEqualTo(100L);
    assertThat(domain.getRoleName()).isEqualTo(RoleName.BOARD_ADMIN);
  }
}
