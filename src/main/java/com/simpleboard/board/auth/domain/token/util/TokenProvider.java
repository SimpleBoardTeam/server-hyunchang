package com.simpleboard.board.auth.domain.token.util;

import com.simpleboard.board.auth.domain.token.vo.Token;
import com.simpleboard.board.auth.domain.token.vo.TokenClaims;

/**
 * <b>TokenProvider</b> Port.
 *
 * <p>토큰 발급/검증을 위한 추상 포트 (예: JWT 구현체)
 *
 * @domain port
 * @since 1.0
 */
public interface TokenProvider {
  /**
   * <b>토큰 발급</b>
   *
   * @param claims 발급용 클레임
   * @return 발급된 토큰
   * @since 1.0
   */
  Token issueToken(TokenClaims claims);

  /**
   * <b>토큰 검증 및 파싱</b>
   *
   * @param token 토큰 원문
   * @return 파싱된 클레임
   * @since 1.0
   */
  TokenClaims verifyAndParseToken(String token);
}
