package com.simpleboard.board.auth.domain.token.service;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.auth.domain.common.vo.Role;
import com.simpleboard.board.auth.domain.token.vo.VerifyPurpose;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TokenTTLPolicyTest {
  private final int accessMinutes = 10;
  private final int refreshDays = 30;
  private final int nicknameMinutes = 15;
  private final int emailMinutes = 5;
  private final int passwordMinutes = 5;

  private final TokenTTLPolicy policy =
      new TokenTTLPolicy(
          accessMinutes, refreshDays, nicknameMinutes, emailMinutes, passwordMinutes);

  @Test
  @DisplayName("Member 역할의 Access TTL 반환 테스트")
  void accessTtlFor_Member_ReturnsConfiguredDuration_Test() {
    // when
    Duration ttl = policy.accessTtlFor(Role.MEMBER);
    // then
    assertThat(ttl).isEqualTo(Duration.ofMinutes(accessMinutes));
  }

  @Test
  @DisplayName("Admin 역할의 Access TTL 반환 테스트")
  void accessTtlFor_Admin_ReturnsHalfConfiguredDuration_Test() {
    // when
    Duration ttl = policy.accessTtlFor(Role.ADMIN);
    // then
    assertThat(ttl).isEqualTo(Duration.ofMinutes(accessMinutes / 2));
  }

  @Test
  @DisplayName("Member 역할의 Refresh TTL 반환 테스트")
  void refreshTtlFor_Member_ReturnsConfiguredDuration_Test() {
    // when
    Duration ttl = policy.refreshTtlFor(Role.MEMBER);
    // then
    assertThat(ttl).isEqualTo(Duration.ofDays(refreshDays));
  }

  @Test
  @DisplayName("Admin 역할의 Refresh TTL 반환 테스트")
  void refreshTtlFor_Admin_ReturnsHalfConfiguredDuration_Test() {
    // when
    Duration ttl = policy.refreshTtlFor(Role.ADMIN);
    // then
    assertThat(ttl).isEqualTo(Duration.ofDays(refreshDays / 2));
  }

  @Test
  @DisplayName("EMAIL 목적의 Verify TTL 반환 테스트")
  void verifyTtlFor_Email_ReturnsConfiguredDuration_Test() {
    // when
    Duration ttl = policy.verifyTtlFor(VerifyPurpose.EMAIL);
    // then
    assertThat(ttl).isEqualTo(Duration.ofMinutes(emailMinutes));
  }

  @Test
  @DisplayName("PASSWORD 목적의 Verify TTL 반환 테스트")
  void verifyTtlFor_Password_ReturnsConfiguredDuration_Test() {
    // when
    Duration ttl = policy.verifyTtlFor(VerifyPurpose.PASSWORD);
    // then
    assertThat(ttl).isEqualTo(Duration.ofMinutes(passwordMinutes));
  }

  @Test
  @DisplayName("NICKNAME 목적의 Verify TTL 반환 테스트")
  void verifyTtlFor_Nickname_ReturnsConfiguredDuration_Test() {
    // when
    Duration ttl = policy.verifyTtlFor(VerifyPurpose.NICKNAME);
    // then
    assertThat(ttl).isEqualTo(Duration.ofMinutes(nicknameMinutes));
  }
}
