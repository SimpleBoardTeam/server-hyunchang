package com.simpleboard.board.board.domain.board.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class InvalidBoardNameException extends ServiceException {
  private static final ErrorCode errorCode = ErrorCode.BOARD_NAME_INVALID;

  public InvalidBoardNameException() {
    super(errorCode, null);
  }

  public InvalidBoardNameException(String message) {
    super(errorCode, message);
  }
}
