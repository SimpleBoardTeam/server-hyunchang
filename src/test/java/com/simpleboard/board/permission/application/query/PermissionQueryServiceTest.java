package com.simpleboard.board.permission.application.query;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.simpleboard.board.permission.application.query.repository.PermissionQueryRepository;
import com.simpleboard.board.permission.domain.PermissionPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PermissionQueryServiceTest {

  @Mock PermissionQueryRepository permissionQueryRepository;

  @InjectMocks PermissionQueryService permissionQueryService;

  PermissionPolicy permissionPolicy;
  Long boardId;
  Long creatorId;

  @BeforeEach
  void setUp() {
    boardId = 1L;
    creatorId = 1L;
    permissionPolicy = PermissionPolicy.create(boardId, creatorId);
  }

  @Test
  void checkBoardDeletePermission_성공_true반환() {
    // when
    when(permissionQueryRepository.getByBoardId(boardId)).thenReturn(permissionPolicy);

    // then
    assertThat(permissionQueryService.checkBoardDeletePermission(boardId, creatorId)).isTrue();
  }

  @Test
  void checkBoardDeletePermission_성공_false_반환() {
    // given
    Long notOwnPermissionUserId = 2L;
    // when
    when(permissionQueryRepository.getByBoardId(boardId)).thenReturn(permissionPolicy);

    // then
    assertThat(permissionQueryService.checkBoardDeletePermission(boardId, notOwnPermissionUserId))
        .isFalse();
  }
}
