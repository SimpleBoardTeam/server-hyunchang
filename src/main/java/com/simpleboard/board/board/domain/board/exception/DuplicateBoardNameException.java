package com.simpleboard.board.board.domain.board.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class DuplicateBoardNameException extends ServiceException {
  private static final ErrorCode errorCode = ErrorCode.BOARD_NAME_DUPLICATED;

  public DuplicateBoardNameException() {
    super(errorCode, null);
  }

  public DuplicateBoardNameException(String message) {
    super(errorCode, message);
  }
}
