package com.simpleboard.board.board.application.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

/**
 * Comment id에 대한 comment가 존재하지 않거나,
 *
 * <p>상태가 ACTIVATE, DELETED 외의 상태일 경우 발생
 */
public class CommentNotFoundException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.COMMENT_NOT_EXISTS;

  public CommentNotFoundException() {
    super(ERROR_CODE);
  }
}
