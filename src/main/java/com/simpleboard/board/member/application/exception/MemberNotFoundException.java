package com.simpleboard.board.member.application.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class MemberNotFoundException extends ServiceException {
  private static final ErrorCode errorCode = ErrorCode.MEMBER_NOT_FOUND;

  public MemberNotFoundException() {
    super(errorCode);
  }
}
