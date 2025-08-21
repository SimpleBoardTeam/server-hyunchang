package com.simpleboard.board.auth.presentation.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

/**
 * <b>ErrorCode Wrapping class</b>
 *
 * <p>ServiceException들 중 SecurityFilterEntryPoint로 보낼 Exception들을 래핑하여 ErrorCode를 전달
 */
public class SecurityFilterException extends AuthenticationException {
  @Getter private final ErrorCode errorCode;

  public SecurityFilterException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
