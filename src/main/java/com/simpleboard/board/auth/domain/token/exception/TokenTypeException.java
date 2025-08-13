package com.simpleboard.board.auth.domain.token.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class TokenTypeException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.INTERNAL_ERROR;

  public TokenTypeException() {
    super(ERROR_CODE);
  }
}
