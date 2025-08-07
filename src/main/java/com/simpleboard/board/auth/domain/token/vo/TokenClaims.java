package com.simpleboard.board.auth.domain.token.vo;

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
    String subject,
    Role role,
    String audience,
    String issuer,
    Instant issueAt,
    Instant expiredAt) {
  public boolean isExpired(Instant now) {
    return !expiredAt.isAfter(now);
  }
}
