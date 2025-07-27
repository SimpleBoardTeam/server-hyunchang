package com.simpleboard.board.global.exception;

import lombok.Getter;

/**
 * <b>Custom Exception Class <b>
 *
 * <p>서비스 단에서 발생하는 "서비스 플로우 내의 예외" 정의를 위한 클래스
 *
 * <p>모든 커스텀 Exception 클래스는 해당 클래스를 extends 하여 구현한다.
 */
@Getter
public abstract class ServiceException extends RuntimeException {

  private final ErrorCode errorCode;

  protected ServiceException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  protected ServiceException(ErrorCode errorCode, String customMsg) {
    super((customMsg == null || customMsg.isBlank()) ? errorCode.getMessage() : customMsg);
    this.errorCode = errorCode;
  }
}
