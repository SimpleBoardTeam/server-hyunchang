package com.simpleboard.board.permission.application.query.repository;

import com.simpleboard.board.permission.domain.entity.PermissionPolicy;

/**
 * PermissionQueryRepository
 *
 * <p>Permission Query 인터페이스
 *
 * @domain repository-port
 */
public interface PermissionQueryRepository {
  PermissionPolicy getByBoardId(Long boardId);
}
