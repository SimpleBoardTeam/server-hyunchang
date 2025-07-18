package com.simpleboard.board.board.domain.post.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class MemberPostPermissionException extends ServiceException {

  private static final ErrorCode ERROR_CODE = ErrorCode.NO_DELETE_PERMISSION;

  public MemberPostPermissionException(String customMsg) {
    super(ERROR_CODE, customMsg);
  }
}
