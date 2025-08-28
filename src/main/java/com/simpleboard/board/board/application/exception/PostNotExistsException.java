package com.simpleboard.board.board.application.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

/**
 * Comment에 연결되어있는 postId의 Post가 존재하지 않거나,
 *
 * <p>soft deleted 상태일 경우 발생
 */
public class PostNotExistsException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.POST_NOT_FOUNT;

  public PostNotExistsException() {
    super(ERROR_CODE);
  }
}
