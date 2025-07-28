package com.simpleboard.board.permission.infrastructure.repository.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.simpleboard.board.permission.application.command.repository.PermissionCommandRepository;
import com.simpleboard.board.permission.domain.PermissionPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(PermissionCommandRepositoryImpl.class) // 수동 등록 필요
class PermissionCommandRepositoryImplTest {

  @Autowired private PermissionCommandRepository permissionCommandRepository;

  @Test
  void save후_getByBoardId로_조회된다() {
    // given
    Long boardId = 1L;
    PermissionPolicy permissionPolicy = PermissionPolicy.create(boardId, 1L);
    PermissionPolicy saved = permissionCommandRepository.save(permissionPolicy);

    // when
    PermissionPolicy found = permissionCommandRepository.getByBoardId(boardId);

    // then
    assertThat(found.getBoardId()).isEqualTo(boardId);
  }

  @DisplayName("delete 후 getByBoardId로 조회 시 예외 발생")
  @Test
  void delete_thenGetByBoardId_throwsException() {
    // given
    Long boardId = 1L;
    PermissionPolicy permissionPolicy = PermissionPolicy.create(boardId, 123L);

    permissionCommandRepository.save(permissionPolicy);

    // when
    permissionCommandRepository.delete(permissionPolicy);

    // then
    assertThatThrownBy(() -> permissionCommandRepository.getByBoardId(boardId))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("해당 boardId의 권한 정책이 존재하지 않습니다");
  }
}
