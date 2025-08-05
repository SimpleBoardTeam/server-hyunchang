package com.simpleboard.board.permission.domain;

/**
 * PermissionCommandRepository
 *
 * <p>Permission Command 인터페이스
 *
 * @domain repository-port
 */
public interface PermissionRepository {
  PermissionPolicy getByBoardId(Long boardId);

  void delete(PermissionPolicy permissionPolicy);

  PermissionPolicy save(PermissionPolicy permissionPolicy);
}
