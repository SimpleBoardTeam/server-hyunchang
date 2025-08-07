package com.simpleboard.board.auth.domain.token.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

/**
 * <b>Refresh token 만료 에러</b>
 *
 * <p>만료된 리프레시 토큰으로 로테이션/블랙리스트 등록을 시도했을 경우 발생
 *
 * @domain exception
 * @since 1.0
 */
public class RefreshTokenExpiredException extends ServiceException {
  private static final ErrorCode ERROR_CODE = ErrorCode.INTERNAL_ERROR;

  public RefreshTokenExpiredException() {
    super(ERROR_CODE);
  }
}
