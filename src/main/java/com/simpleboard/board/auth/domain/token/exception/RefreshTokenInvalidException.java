package com.simpleboard.board.auth.domain.token.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

/**
 * <b>Refresh token 무효 에러</b>
 *
 * <p>블랙리스트에 이미 존재하거나 규격 위반 등으로 무효한 리프레시 토큰
 *
 * @domain exception
 * @since 1.0
 */
public class RefreshTokenInvalidException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.INTERNAL_ERROR;

  public RefreshTokenInvalidException() {
    super(ERROR_CODE);
  }
}
