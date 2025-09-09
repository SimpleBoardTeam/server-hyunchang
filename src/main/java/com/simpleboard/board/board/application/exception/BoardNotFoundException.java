package com.simpleboard.board.board.application.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class BoardNotFoundException extends ServiceException {
  private static final ErrorCode errorCode = ErrorCode.BOARD_NOT_FOUND;

  public BoardNotFoundException() {
    super(errorCode);
  }
}
