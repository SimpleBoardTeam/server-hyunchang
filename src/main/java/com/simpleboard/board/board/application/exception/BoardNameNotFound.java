package com.simpleboard.board.board.application.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class BoardNameNotFound extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.BOARDNAME_NOT_FOUND;

  public BoardNameNotFound() {
    super(ERROR_CODE);
  }
}
