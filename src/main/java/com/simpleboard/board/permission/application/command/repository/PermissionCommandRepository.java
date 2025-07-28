package com.simpleboard.board.permission.application.command.repository;

import com.simpleboard.board.permission.domain.PermissionPolicy;

/**
 * PermissionCommandRepository
 *
 * <p>Permission Command 인터페이스
 *
 * @domain repository-port
 */
public interface PermissionCommandRepository {
  PermissionPolicy getByBoardId(Long boardId);

  void delete(PermissionPolicy permissionPolicy);

  PermissionPolicy save(PermissionPolicy permissionPolicy);
}
