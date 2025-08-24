package com.simpleboard.board.permission.domain.repository;

import com.simpleboard.board.permission.domain.entity.PermissionPolicy;

/**
 * PermissionRepository
 *
 * <p>PermissionRepository 인터페이스
 *
 * @domain repository-port
 */
public interface PermissionRepository {
  PermissionPolicy getByBoardId(Long boardId);

  void delete(PermissionPolicy permissionPolicy);

  PermissionPolicy save(PermissionPolicy permissionPolicy);
}
