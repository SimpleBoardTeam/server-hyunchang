package com.simpleboard.board.permission.domain.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class AssignmentNotFoundException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.ASSIGNMENT_NOT_FOUND;

  public AssignmentNotFoundException() {
    super(ERROR_CODE, "");
  }
}
