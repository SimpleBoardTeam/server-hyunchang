package com.simpleboard.board.auth.domain.token.service;

import com.simpleboard.board.auth.domain.common.vo.Role;
import com.simpleboard.board.auth.domain.token.vo.VerifyPurpose;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <b>TokenTTLPolicy</b> Domain Service.
 *
 * <p>역할/목적에 따른 토큰 TTL 정책 정의
 *
 * @domain domain-service
 * @since 1.0
 */
@Component
public class TokenTTLPolicy {

  private final int accessMinutes;
  private final int refreshDays;
  private final int nicknameMinutes;
  private final int emailMinutes;
  private final int passwordMinutes;

  public TokenTTLPolicy(
      @Value("${app.auth.ttl.access-minutes:10}") int accessMinutes,
      @Value("${app.auth.ttl.refresh-days:30}") int refreshDays,
      @Value("${app.auth.ttl.nickname-minutes:15}") int nicknameMinutes,
      @Value("${app.auth.ttl.email-minutes:5}") int emailMinutes,
      @Value("${app.auth.ttl.password-minutes:5}") int passwordMinutes) {
    this.accessMinutes = accessMinutes;
    this.refreshDays = refreshDays;
    this.nicknameMinutes = nicknameMinutes;
    this.emailMinutes = emailMinutes;
    this.passwordMinutes = passwordMinutes;
  }

  /**
   * <b>역할별 Access TTL</b>
   *
   * <p>ADMIN은 더 짧은 TTL
   *
   * @since 1.0
   */
  public Duration accessTtlFor(Role role) {
    switch (role) {
      case MEMBER:
        return Duration.ofMinutes(accessMinutes);
      case ADMIN:
        return Duration.ofMinutes(accessMinutes / 2);
      default:
        return Duration.ZERO;
    }
  }

  /**
   * <b>역할별 Refresh TTL</b>
   *
   * <p>ADMIN은 더 짧은 TTL
   *
   * @since 1.0
   */
  public Duration refreshTtlFor(Role role) {
    switch (role) {
      case MEMBER:
        return Duration.ofDays(refreshDays);
      case ADMIN:
        return Duration.ofDays(refreshDays / 2);
      default:
        return Duration.ZERO;
    }
  }

  /**
   * <b>목적별 Verify TTL</b>
   *
   * <p>EMAIL / PASSWORD / NICKNAME 검증 목적별 TTL
   *
   * @since 1.0
   */
  public Duration verifyTtlFor(VerifyPurpose purpose) {
    switch (purpose) {
      case EMAIL:
        return Duration.ofMinutes(emailMinutes);
      case PASSWORD:
        return Duration.ofMinutes(passwordMinutes);
      case NICKNAME:
        return Duration.ofMinutes(nicknameMinutes);
      default:
        return Duration.ZERO;
    }
  }
}
