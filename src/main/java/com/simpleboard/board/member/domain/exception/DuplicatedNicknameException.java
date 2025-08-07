package com.simpleboard.board.member.domain.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class DuplicatedNicknameException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.DUPLICATED_NICKNAME;

  public DuplicatedNicknameException() {
    super(ERROR_CODE);
  }

}
