package com.simpleboard.board.auth.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "email_user_auth")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailUserAuthEntity extends UserAuthEntity {

  @Column(name = "email", unique = true, nullable = false, length = 255)
  private String email;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Override
  public String getLoginId() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }
}
