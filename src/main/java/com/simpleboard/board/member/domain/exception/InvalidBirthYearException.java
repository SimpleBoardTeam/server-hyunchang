package com.simpleboard.board.member.domain.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

public class InvalidBirthYearException extends ServiceException {

  private static final ErrorCode ERROR_CODE = ErrorCode.INVALID_BIRTH_YEAR;

  public InvalidBirthYearException(String customMessage) {
    super(ERROR_CODE, customMessage);
  }

  public InvalidBirthYearException() {
    super(ERROR_CODE);
  }
}
