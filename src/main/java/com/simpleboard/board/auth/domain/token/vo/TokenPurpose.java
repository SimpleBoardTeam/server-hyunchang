package com.simpleboard.board.auth.domain.token.vo;

/**
 * <b>TokenPurpose</b> Enum.
 *
 * <p>토큰의 목적: ACCESS / REFRESH / VERIFY
 *
 * @domain enum
 * @since 1.0
 */
public enum TokenPurpose {
  ACCESS,
  REFRESH,
  VERIFY,
  ;
}
