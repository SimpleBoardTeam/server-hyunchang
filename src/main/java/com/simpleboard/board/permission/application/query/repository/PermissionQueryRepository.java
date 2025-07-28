package com.simpleboard.board.permission.application.query.repository;

import com.simpleboard.board.permission.domain.PermissionPolicy;

public interface PermissionQueryRepository {
  PermissionPolicy getByBoardId(Long boardId);
}
