package com.simpleboard.board.board.infrastructure.webclient.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class BoardDeleteNotAllowedException extends ServiceException {

  private static final ErrorCode errorCode = ErrorCode.BOARD_NO_DELETE_PERMISSION;

  public BoardDeleteNotAllowedException() {
    super(errorCode);
  }
}
