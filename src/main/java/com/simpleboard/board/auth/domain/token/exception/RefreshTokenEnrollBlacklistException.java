package com.simpleboard.board.auth.domain.token.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

/**
 * <b>Refresh token 블랙리스트 등록 에러</b>
 *
 * <p>REFRESH 토큰이 아닌 경우 등, 블랙리스트 등록 요건 미충족 시 발생
 *
 * @domain exception
 * @since 1.0
 */
public class RefreshTokenEnrollBlacklistException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.INTERNAL_ERROR;

  public RefreshTokenEnrollBlacklistException() {
    super(ERROR_CODE);
  }
}
