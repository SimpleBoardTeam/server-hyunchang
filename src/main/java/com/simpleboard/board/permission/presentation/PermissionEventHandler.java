package com.simpleboard.board.permission.presentation;

import com.simpleboard.board.permission.presentation.tmp.BoardCreatedEvent;
import com.simpleboard.board.permission.presentation.tmp.BoardDeletedEvent;

public interface PermissionEventHandler {

  void handleBoardCreated(BoardCreatedEvent event);

  void handleBoardDeleted(BoardDeletedEvent event);
}