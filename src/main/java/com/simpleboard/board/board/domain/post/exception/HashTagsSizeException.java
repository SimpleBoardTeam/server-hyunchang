package com.simpleboard.board.board.domain.post.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class HashTagsSizeException extends ServiceException {

  private static final ErrorCode ERROR_CODE = ErrorCode.HASHTAG_SIZE_EXCEED;

  public HashTagsSizeException(String customMsg) {
    super(ERROR_CODE, customMsg);
  }
}
