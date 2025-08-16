package com.simpleboard.board.auth.application.service;

import com.simpleboard.board.auth.domain.token.vo.Token;
import com.simpleboard.board.auth.domain.token.vo.TokenPair;
import com.simpleboard.board.auth.domain.token.vo.VerifyPurpose;

/**
 * <b>Token과 관련된 기능을 수행하는 Application Service 클래스</b>
 *
 * <p>로그인 토큰의 발행은 AuthPrincipalService 에서 처리
 *
 * <p>AuthPrincipal과 상관없는 토큰 기능을 수행
 */
public interface TokenApplicationService {
  /**
   * <b>Refresh 토큰 Rotation 메서드</b>
   *
   * <p>토큰 validation 후, 기존 토큰을 비활성화, 새로운 로그인 토큰 페어 발행
   *
   * @since 1.0
   */
  TokenPair rotateRefreshToken(String refreshTokenRaw);

  /**
   * <b>Verify용 토큰 발행 메서드</b>
   *
   * @since 1.0
   */
  Token issueVerifyToken(String subject, VerifyPurpose purpose);

  /**
   * <b>로그아웃 등의 정상 동작 내 토큰 비활성화 메서드</b>
   *
   * @param tokenRaw
   */
  void enrollBlacklist(String tokenRaw);

  /**
   * <b>비정상 동작, 토큰 탈취 감지시 대응 메서드</b>
   *
   * <ul>
   *   <li>토큰 비활성화
   *   <li>유저 UUID 재발급
   * </ul>
   *
   * @param tokenRaw
   */
  void blockSnatchedToken(String tokenRaw);
}
