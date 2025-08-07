package com.simpleboard.board.auth.domain.token.vo;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TokenClaimsTest {
  @Test
  @DisplayName("isExpired(now): expiredAt > now 이면 미만료, <= now 이면 만료")
  void isExpired_semantics() {
    Instant now = Instant.ofEpochSecond(1_700_000_000L);
    TokenClaims notExpired = TokenClaims.builder().expiredAt(now.plusSeconds(1)).build();
    TokenClaims expiredEq = TokenClaims.builder().expiredAt(now).build();
    TokenClaims expiredLt = TokenClaims.builder().expiredAt(now.minusSeconds(1)).build();

    assertThat(notExpired.isExpired(now)).isFalse();
    assertThat(expiredEq.isExpired(now)).isTrue();
    assertThat(expiredLt.isExpired(now)).isTrue();
  }
}
