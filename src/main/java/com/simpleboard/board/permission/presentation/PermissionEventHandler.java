package com.simpleboard.board.permission.presentation;

import com.simpleboard.board.permission.application.command.PermissionCommandService;
import com.simpleboard.board.permission.presentation.tmp.BoardCreatedEvent;
import com.simpleboard.board.permission.presentation.tmp.BoardDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionEventHandler {

  private final PermissionCommandService permissionCommandService;

  @Async
  @EventListener
  public void handleBoardCreated(BoardCreatedEvent event) {
    permissionCommandService.createPermissionPolicy(event.boardId(), event.userId());
  }

  @Async
  @EventListener
  public void handleBoardDeleted(BoardDeletedEvent event) {
    permissionCommandService.deletePermissionPolicy(event.boardId());
  }
}
