package com.simpleboard.board.board.domain.post.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class PostPasswordNotMatchException extends ServiceException {

  private static final ErrorCode ERROR_CODE = ErrorCode.POST_PASSWORD_NOT_MATCH;

  public PostPasswordNotMatchException(String customMsg) {
    super(ERROR_CODE, customMsg);
  }
}
