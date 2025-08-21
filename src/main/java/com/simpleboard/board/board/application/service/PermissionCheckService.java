package com.simpleboard.board.board.application.service;

public interface PermissionCheckService {
  void checkBoardCreatePermission(Long memberId);

  void checkBoardDeletePermission(Long memberId, Long boardId);
}
