package com.simpleboard.board.auth.application.service;

import com.simpleboard.board.auth.domain.token.vo.TokenPair;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <b>Authentication 응용 서비스 클래스</b>
 *
 * <p>Spring Security의 UserDetails를 implements한 AuthPrincipal 클래스와 관련된 일을 하는 응용 서비스
 *
 * <ul>
 *   <li>AuthPrincipal에 대한 조회 및 검증
 *   <li>AuthPrincipal을 통한 로그인 토큰 발급
 * </ul>
 *
 * @domain application-service
 * @transactional
 */
public interface AuthPrincipalService extends UserDetailsService {
  /**
   * <b>Email/password 로그인용 메서드</b>
   *
   * <p>email을 통해 UserAuth를 조회, AuthPrincipal을 로딩
   *
   * @since 1.0
   */
  public UserDetails loadUserByUsername(String email);

  /**
   * <b>OAuth 로그인용 메서드</b>
   *
   * <p>OAuth id를 통해 UserAuth를 조회, AuthPrincipal을 로딩
   *
   * @since 1.0
   */
  public UserDetails loadUserByOAuthId(String oAuthId);

  /**
   * <b>Access 토큰용 메서드</b>
   *
   * <p>Access 토큰을 파싱하여 AuthPrincipal을 로딩
   *
   * @since 1.0
   */
  public UserDetails loadUserByTokenRaw(String tokenRaw);

  /**
   * <b>로그인 토큰 페어 발행 메서드</b>
   *
   * <p>AuthPrincipal을 통해 로그인 토큰 페어를 발행
   *
   * @since 1.0
   */
  public TokenPair issueTokenPair(AuthPrincipal principal);
}
