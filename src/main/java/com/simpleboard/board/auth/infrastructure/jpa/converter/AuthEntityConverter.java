package com.simpleboard.board.auth.infrastructure.jpa.converter;

import com.simpleboard.board.auth.domain.auth.entity.EmailUserAuth;
import com.simpleboard.board.auth.domain.auth.entity.OAuthUserAuth;
import com.simpleboard.board.auth.domain.auth.entity.UserAuth;
import com.simpleboard.board.auth.infrastructure.jpa.entity.EmailUserAuthEntity;
import com.simpleboard.board.auth.infrastructure.jpa.entity.OAuthUserAuthEntity;
import com.simpleboard.board.auth.infrastructure.jpa.entity.UserAuthEntity;
import org.springframework.stereotype.Component;

/**
 * <b>UserAuth domain <-> infra Converter</b>
 *
 * <p>도메인 엔티티 <-> DB용 infra 엔티티 간의 변환을 담당
 */
@Component
public class AuthEntityConverter {

  public UserAuthEntity toJpaEntity(UserAuth domain) {

    if (domain instanceof EmailUserAuth) {
      EmailUserAuth emailDomain = (EmailUserAuth) domain;
      return EmailUserAuthEntity.builder()
          .userId(domain.getUserId())
          .userState(domain.getUserState())
          .registerType(domain.getRegisterType())
          .role(domain.getRole())
          .email(emailDomain.getEmail())
          .password(emailDomain.getPassword())
          .build();
    }
    if (domain instanceof OAuthUserAuth) {
      OAuthUserAuth oAuthDomain = (OAuthUserAuth) domain;
      return OAuthUserAuthEntity.builder()
          .userId(domain.getUserId())
          .userState(domain.getUserState())
          .registerType(domain.getRegisterType())
          .role(domain.getRole())
          .OAuthId(oAuthDomain.getOAuthId())
          .build();
    }
    throw new IllegalArgumentException("Unsupported auth type: " + domain.getClass());
  }

  public UserAuth toDomainEntity(UserAuthEntity jpa) {
    // TODO: userState 체크
    if (jpa instanceof EmailUserAuthEntity) {
      EmailUserAuthEntity emailJpa = (EmailUserAuthEntity) jpa;
      return EmailUserAuth.builder()
          .userId(jpa.getUserId())
          .userState(jpa.getUserState())
          .registerType(jpa.getRegisterType())
          .role(jpa.getRole())
          .email(emailJpa.getEmail())
          .password(emailJpa.getPassword())
          .build();
    }
    if (jpa instanceof OAuthUserAuthEntity) {
      OAuthUserAuthEntity oAuthJpa = (OAuthUserAuthEntity) jpa;
      return OAuthUserAuth.builder()
          .userId(jpa.getUserId())
          .userState(jpa.getUserState())
          .registerType(jpa.getRegisterType())
          .role(jpa.getRole())
          .OAuthId(oAuthJpa.getOAuthId())
          .build();
    }
    throw new IllegalArgumentException("Unsupported auth type: " + jpa.getClass());
  }
}
