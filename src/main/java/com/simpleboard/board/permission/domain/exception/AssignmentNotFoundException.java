package com.simpleboard.board.permission.domain.exception;

public class AssignmentNotFoundException extends RuntimeException {
  public AssignmentNotFoundException(Long userId) {
    super("userId=" + userId + " 에 대한 할당된 권한이 존재하지 않습니다.");
  }
}