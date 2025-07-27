package com.simpleboard.board.permission.domain.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class RoleDelegationException extends ServiceException {
  public static final ErrorCode ERROR_CODE = ErrorCode.ROLE_NOT_ASSIGNED;

  public RoleDelegationException() {
    super(ERROR_CODE, "");
  }
}
