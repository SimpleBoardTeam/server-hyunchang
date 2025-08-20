package com.simpleboard.board.auth.application.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

/** <b>Member의 상태가 Activate가 아닐 때 발생되는 Exception</b> */
public class UserDisabledException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.USER_DEACTIVATED;

  public UserDisabledException() {
    super(ERROR_CODE);
  }
}
