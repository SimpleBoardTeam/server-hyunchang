package com.simpleboard.board.auth.application.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

/** <b>로그인 및 Access 토큰 로딩시 해당하는 유저를 발견하지 못함</b> */
public class UserNotFoundException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.USER_NOT_FOUND;

  public UserNotFoundException() {
    super(ERROR_CODE);
  }
}
