package com.simpleboard.board.auth.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "oauth_user_auth")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthUserAuthEntity extends UserAuthEntity {

  @Column(name = "oauth_id", unique = true, nullable = false, length = 255)
  private String OAuthId;

  @Override
  public String getLoginId() {
    return OAuthId;
  }

  @Override
  public String getPassword() {
    return "";
  }
}
