package com.simpleboard.board.auth.domain.token.vo;

import com.simpleboard.board.auth.domain.common.vo.Role;
import java.time.Instant;
import lombok.Builder;

/**
 * <b>TokenClaims</b> Value Object
 *
 * <p>토큰 발급/검증에 사용되는 클레임 집합
 *
 * @domain value-object
 * @since 1.0
 */
@Builder
public record TokenClaims(
    String tokenId,
    TokenPurpose tokenPurpose,
    VerifyPurpose verifyPurpose,
    String subject, // login 토큰의 경우 memberUUID, verify token의 경우 verify 대상(email, nickname 등)
    Role role,
    String audience,
    String issuer,
    Instant issueAt,
    Instant expiredAt) {
  public boolean isExpired(Instant now) {
    return !expiredAt.isAfter(now);
  }
}
