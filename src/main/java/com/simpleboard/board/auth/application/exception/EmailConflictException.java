package com.simpleboard.board.auth.application.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class EmailConflictException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.EMAIL_CONFLICT;

  public EmailConflictException() {
    super(ERROR_CODE);
  }
}
