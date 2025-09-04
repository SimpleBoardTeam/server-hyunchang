package com.simpleboard.board.auth.infrastructure.jwt.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class TokenParseException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.TOKEN_PARSE_FAIL;

  public TokenParseException() {
    super(ERROR_CODE);
  }
}
