package com.simpleboard.board.board.domain.comment.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

/** GuestComment 삭제시 password 불일치 exception */
public class CommentPasswordException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.COMMENT_PASSWORD_NOT_MATCH;

  public CommentPasswordException() {
    super(ERROR_CODE, null);
  }
}
