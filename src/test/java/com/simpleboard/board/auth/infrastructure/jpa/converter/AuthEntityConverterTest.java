package com.simpleboard.board.auth.infrastructure.jpa.converter;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.auth.domain.auth.dto.request.RegisterParams;
import com.simpleboard.board.auth.domain.auth.entity.EmailUserAuth;
import com.simpleboard.board.auth.domain.auth.entity.OAuthUserAuth;
import com.simpleboard.board.auth.domain.auth.entity.UserAuth;
import com.simpleboard.board.auth.domain.auth.vo.RegisterType;
import com.simpleboard.board.auth.domain.auth.vo.UserState;
import com.simpleboard.board.auth.domain.common.vo.Role;
import com.simpleboard.board.auth.infrastructure.jpa.entity.EmailUserAuthEntity;
import com.simpleboard.board.auth.infrastructure.jpa.entity.OAuthUserAuthEntity;
import com.simpleboard.board.auth.infrastructure.jpa.entity.UserAuthEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthEntityConverterTest {

  private final AuthEntityConverter converter = new AuthEntityConverter();

  @Test
  @DisplayName("toJpaEntity: EmailUserAuth → EmailUserAuthEntity 매핑")
  void toJpaEntity_Email_Success() {
    // given
    EmailUserAuth domain =
        EmailUserAuth.builder()
            .userId(1L)
            .userState(UserState.ACTIVE)
            .registerType(RegisterType.EMAIL)
            .role(Role.MEMBER)
            .email("user@example.com")
            .password("pw-enc")
            .build();

    // when
    UserAuthEntity jpa = converter.toJpaEntity(domain);

    // then
    assertThat(jpa).isInstanceOf(EmailUserAuthEntity.class);
    EmailUserAuthEntity email = (EmailUserAuthEntity) jpa;
    assertThat(email.getUserId()).isEqualTo(1L);
    assertThat(email.getUserState()).isEqualTo(UserState.ACTIVE);
    assertThat(email.getRegisterType()).isEqualTo(RegisterType.EMAIL);
    assertThat(email.getRole()).isEqualTo(Role.MEMBER);
    assertThat(email.getEmail()).isEqualTo("user@example.com");
    assertThat(email.getPassword()).isEqualTo("pw-enc");
    // 엔티티 동작 확인(무의미한 회귀 방지용)
    assertThat(email.getLoginId()).isEqualTo("user@example.com");
  }

  @Test
  @DisplayName("toJpaEntity: OAuthUserAuth → OAuthUserAuthEntity 매핑")
  void toJpaEntity_OAuth_Success() {
    // given
    OAuthUserAuth domain =
        OAuthUserAuth.builder()
            .userId(2L)
            .userState(UserState.ACTIVE)
            .registerType(RegisterType.GOOGLE)
            .role(Role.ADMIN)
            .OAuthId("google-123")
            .build();

    // when
    UserAuthEntity jpa = converter.toJpaEntity(domain);

    // then
    assertThat(jpa).isInstanceOf(OAuthUserAuthEntity.class);
    OAuthUserAuthEntity oauth = (OAuthUserAuthEntity) jpa;
    assertThat(oauth.getUserId()).isEqualTo(2L);
    assertThat(oauth.getUserState()).isEqualTo(UserState.ACTIVE);
    assertThat(oauth.getRegisterType()).isEqualTo(RegisterType.GOOGLE);
    assertThat(oauth.getRole()).isEqualTo(Role.ADMIN);
    assertThat(oauth.getOAuthId()).isEqualTo("google-123");
    assertThat(oauth.getLoginId()).isEqualTo("google-123");
  }

  @Test
  @DisplayName("toJpaEntity: 지원하지 않는 도메인 타입이면 IllegalArgumentException")
  void toJpaEntity_Unsupported_Throws() {
    // given: Email/OAuth가 아닌 임의의 도메인 서브클래스
    UserAuth unknown =
        new UnknownUserAuth(
            RegisterParams.builder()
                .role(Role.MEMBER)
                .registerType(RegisterType.EMAIL)
                .email("x@x.com")
                .password("x")
                .OAuthId(null)
                .build());

    // expect
    assertThatThrownBy(() -> converter.toJpaEntity(unknown))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unsupported auth type");
  }

  @Test
  @DisplayName("toDomainEntity: EmailUserAuthEntity → EmailUserAuth 매핑")
  void toDomainEntity_Email_Success() {
    // given
    EmailUserAuthEntity jpa =
        EmailUserAuthEntity.builder()
            .userId(10L)
            .userState(UserState.ACTIVE)
            .registerType(RegisterType.EMAIL)
            .role(Role.MEMBER)
            .email("mail@ex.com")
            .password("hash")
            .build();

    // when
    UserAuth domain = converter.toDomainEntity(jpa);

    // then
    assertThat(domain).isInstanceOf(EmailUserAuth.class);
    EmailUserAuth email = (EmailUserAuth) domain;
    assertThat(email.getUserId()).isEqualTo(10L);
    assertThat(email.getUserState()).isEqualTo(UserState.ACTIVE);
    assertThat(email.getRegisterType()).isEqualTo(RegisterType.EMAIL);
    assertThat(email.getRole()).isEqualTo(Role.MEMBER);
    assertThat(email.getEmail()).isEqualTo("mail@ex.com");
    assertThat(email.getPassword()).isEqualTo("hash");
    assertThat(email.getLoginId()).isEqualTo("mail@ex.com");
  }

  @Test
  @DisplayName("toDomainEntity: OAuthUserAuthEntity → OAuthUserAuth 매핑")
  void toDomainEntity_OAuth_Success() {
    // given
    OAuthUserAuthEntity jpa =
        OAuthUserAuthEntity.builder()
            .userId(11L)
            .userState(UserState.ACTIVE)
            .registerType(RegisterType.KAKAO)
            .role(Role.ADMIN)
            .OAuthId("kakao-777")
            .build();

    // when
    UserAuth domain = converter.toDomainEntity(jpa);

    // then
    assertThat(domain).isInstanceOf(OAuthUserAuth.class);
    OAuthUserAuth oauth = (OAuthUserAuth) domain;
    assertThat(oauth.getUserId()).isEqualTo(11L);
    assertThat(oauth.getUserState()).isEqualTo(UserState.ACTIVE);
    assertThat(oauth.getRegisterType()).isEqualTo(RegisterType.KAKAO);
    assertThat(oauth.getRole()).isEqualTo(Role.ADMIN);
    assertThat(oauth.getOAuthId()).isEqualTo("kakao-777");
    assertThat(oauth.getLoginId()).isEqualTo("kakao-777");
    // OAuth 도메인 비밀번호는 "-" 반환(도메인 구현 확인 목적)
    assertThat(oauth.getPassword()).isEqualTo("-");
  }

  @Test
  @DisplayName("toDomainEntity: 지원하지 않는 JPA 타입이면 IllegalArgumentException")
  void toDomainEntity_Unsupported_Throws() {
    // given: Email/OAuth가 아닌 임의의 엔티티 서브클래스
    UserAuthEntity unknown =
        new UnknownUserAuthEntity(99L, UserState.ACTIVE, RegisterType.EMAIL, Role.MEMBER);

    // expect
    assertThatThrownBy(() -> converter.toDomainEntity(unknown))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unsupported auth type");
  }

  @Test
  @DisplayName("Round-trip: Email 도메인 → JPA → 도메인 변환 시 정보 보존")
  void roundTrip_Email() {
    // given
    EmailUserAuth origin =
        EmailUserAuth.builder()
            .userId(100L)
            .userState(UserState.ACTIVE)
            .registerType(RegisterType.EMAIL)
            .role(Role.MEMBER)
            .email("rt@ex.com")
            .password("pwhash")
            .build();

    // when
    UserAuthEntity jpa = converter.toJpaEntity(origin);
    UserAuth roundTripped = converter.toDomainEntity(jpa);

    // then
    assertThat(roundTripped).isInstanceOf(EmailUserAuth.class);
    EmailUserAuth e = (EmailUserAuth) roundTripped;
    assertThat(e.getUserId()).isEqualTo(100L);
    assertThat(e.getUserState()).isEqualTo(UserState.ACTIVE);
    assertThat(e.getRegisterType()).isEqualTo(RegisterType.EMAIL);
    assertThat(e.getRole()).isEqualTo(Role.MEMBER);
    assertThat(e.getEmail()).isEqualTo("rt@ex.com");
    assertThat(e.getPassword()).isEqualTo("pwhash");
  }

  @Test
  @DisplayName("Round-trip: OAuth 도메인 → JPA → 도메인 변환 시 정보 보존")
  void roundTrip_OAuth() {
    // given
    OAuthUserAuth origin =
        OAuthUserAuth.builder()
            .userId(200L)
            .userState(UserState.ACTIVE)
            .registerType(RegisterType.GOOGLE)
            .role(Role.ADMIN)
            .OAuthId("g-200")
            .build();

    // when
    UserAuthEntity jpa = converter.toJpaEntity(origin);
    UserAuth roundTripped = converter.toDomainEntity(jpa);

    // then
    assertThat(roundTripped).isInstanceOf(OAuthUserAuth.class);
    OAuthUserAuth o = (OAuthUserAuth) roundTripped;
    assertThat(o.getUserId()).isEqualTo(200L);
    assertThat(o.getUserState()).isEqualTo(UserState.ACTIVE);
    assertThat(o.getRegisterType()).isEqualTo(RegisterType.GOOGLE);
    assertThat(o.getRole()).isEqualTo(Role.ADMIN);
    assertThat(o.getOAuthId()).isEqualTo("g-200");
    assertThat(o.getLoginId()).isEqualTo("g-200");
  }

  /* ===================== Test-only fixtures ===================== */

  /** Email/OAuth가 아닌 임의의 도메인 타입(예외 케이스 확인용) */
  static class UnknownUserAuth extends UserAuth {
    UnknownUserAuth(RegisterParams params) {
      super(params); // userState=ACTIVE, role/registerType은 params로 세팅
    }

    @Override
    public String getLoginId() {
      return "unknown";
    }

    @Override
    public String getPassword() {
      return "unknown";
    }
  }

  /** Email/OAuth가 아닌 임의의 JPA 엔티티 타입(예외 케이스 확인용) */
  static class UnknownUserAuthEntity extends UserAuthEntity {
    public UnknownUserAuthEntity(Long id, UserState state, RegisterType type, Role role) {
      super(id, state, type, role);
    }

    @Override
    public String getLoginId() {
      return "unknown";
    }

    @Override
    public String getPassword() {
      return "unknown";
    }
  }
}
