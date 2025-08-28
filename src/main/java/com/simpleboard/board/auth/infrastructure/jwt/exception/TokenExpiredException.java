package com.simpleboard.board.auth.infrastructure.jwt.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class TokenExpiredException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.TOKEN_EXPIRED;

  public TokenExpiredException() {
    super(ERROR_CODE);
  }
}
