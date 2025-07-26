package com.simpleboard.board.permission.domain.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class AssignmentNotFoundException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.ASSIGNMENT_NOT_FOUND;

  public AssignmentNotFoundException(Long userId) {
    super(ERROR_CODE, "userId=" + userId + " 에 대한 할당된 권한이 존재하지 않습니다.");
  }
}
