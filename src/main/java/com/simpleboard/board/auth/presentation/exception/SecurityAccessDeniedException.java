package com.simpleboard.board.auth.presentation.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.security.access.AccessDeniedException;

public class SecurityAccessDeniedException extends AccessDeniedException {
  @Getter private final ErrorCode errorCode;

  public SecurityAccessDeniedException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
