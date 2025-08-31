package com.simpleboard.board.permission.presentation;

import static org.mockito.Mockito.*;

import com.simpleboard.board.permission.application.command.PermissionCommandService;
import com.simpleboard.board.permission.presentation.tmp.BoardCreatedEvent;
import com.simpleboard.board.permission.presentation.tmp.BoardDeletedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ContextConfiguration(classes = PermissionEventHandlerImplTest.TestConfig.class)
class PermissionEventHandlerImplTest {

  @Autowired private ApplicationEventPublisher eventPublisher;

  @Autowired private PermissionCommandService permissionCommandService;

  @Test
  @DisplayName("BoardCreatedEvent 발생 시 createPermissionPolicy가 호출된다")
  void handleBoardCreatedEvent() {
    // given
    Long boardId = 1L;
    Long userId = 100L;

    // when
    eventPublisher.publishEvent(new BoardCreatedEvent(boardId, userId));

    // then
    verify(permissionCommandService, timeout(500)).createPermissionPolicy(boardId, userId);
  }

  @Test
  @DisplayName("BoardDeletedEvent 발생 시 deletePermissionPolicy가 호출된다")
  void handleBoardDeletedEvent() {
    // given
    Long boardId = 2L;

    // when
    eventPublisher.publishEvent(new BoardDeletedEvent(boardId));

    // then
    verify(permissionCommandService, timeout(500)).deletePermissionPolicy(boardId);
  }

  @Configuration
  @Import(PermissionEventHandlerImpl.class)
  static class TestConfig {

    @Bean
    public PermissionCommandService permissionCommandService() {
      return mock(PermissionCommandService.class);
    }
  }
}
