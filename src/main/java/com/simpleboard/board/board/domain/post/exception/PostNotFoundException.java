package com.simpleboard.board.board.domain.post.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class PostNotFoundException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.POST_NOT_FOUNT;

  public PostNotFoundException() {
    super(ERROR_CODE);
  }
}
