package com.simpleboard.board.permission.application.command.repository;

import com.simpleboard.board.permission.domain.PermissionPolicy;

public interface PermissionCommandRepository {
  PermissionPolicy getByBoardId(Long boardId);

  void delete(PermissionPolicy permissionPolicy);

  PermissionPolicy save(PermissionPolicy permissionPolicy);
}
