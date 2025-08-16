package com.simpleboard.board.auth.application.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class EmailCodeException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.EMAIL_CODE_MISMATCH;

  public EmailCodeException() {
    super(ERROR_CODE);
  }
}
