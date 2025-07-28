package com.simpleboard.board.permission.infrastructure.repository.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.simpleboard.board.permission.domain.PermissionPolicy;
import com.simpleboard.board.permission.infrastructure.entity.PermissionPolicyEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(PermissionQueryRepositoryImpl.class)
class PermissionQueryRepositoryImplTest {

  @Autowired private PermissionQueryRepositoryImpl repository;

  @Autowired private PermissionQueryJpaRepository jpaRepository;

  @DisplayName("존재하는 boardId로 조회 시, 도메인 객체 반환")
  @Test
  void getByBoardId_success() {
    // given
    Long boardId = 1L;
    PermissionPolicy permissionPolicy = PermissionPolicy.create(boardId, 123L);
    jpaRepository.save(PermissionPolicyEntity.from(permissionPolicy));

    // when
    PermissionPolicy result = repository.getByBoardId(boardId);

    // then
    assertThat(result.getBoardId()).isEqualTo(boardId);
  }

  @DisplayName("존재하지 않는 boardId로 조회 시 예외 발생")
  @Test
  void getByBoardId_notFound() {
    // given
    Long boardId = 999L;

    // when & then
    assertThatThrownBy(() -> repository.getByBoardId(boardId))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("해당 boardId의 권한 정책이 존재하지 않습니다");
  }
}
