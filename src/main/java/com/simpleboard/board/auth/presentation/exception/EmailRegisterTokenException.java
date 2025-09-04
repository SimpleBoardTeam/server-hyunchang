package com.simpleboard.board.auth.presentation.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class EmailRegisterTokenException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.MISSING_REGISTER_TOKEN;

  public EmailRegisterTokenException() {
    super(ERROR_CODE);
  }
}
