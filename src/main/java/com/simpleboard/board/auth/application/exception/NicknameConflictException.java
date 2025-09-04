package com.simpleboard.board.auth.application.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class NicknameConflictException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.NICKNAME_CONFLICT;

  public NicknameConflictException() {
    super(ERROR_CODE);
  }
}
