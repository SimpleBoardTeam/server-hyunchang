package com.simpleboard.board.permission.application.query;

import com.simpleboard.board.permission.application.query.repository.PermissionQueryRepository;
import com.simpleboard.board.permission.domain.Permission;
import com.simpleboard.board.permission.domain.PermissionPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionQueryService {
  private final PermissionQueryRepository permissionQueryRepository;

  public boolean checkBoardDeletePermission(Long boardId, Long userId) {
    PermissionPolicy permissionPolicy = permissionQueryRepository.getByBoardId(boardId);
    return permissionPolicy.can(userId, Permission.DELETE_BOARD);
  }
}
