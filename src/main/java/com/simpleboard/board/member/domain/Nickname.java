package com.simpleboard.board.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <b>Nickname</b> Value Object.
 *
 * <p>회원의 닉네임을 캡슐화하여 유효성 검사 및 도메인 간 명확한 책임 분리를 위한 VO이다.
 *
 * @domain value-object
 * @since 1.0
 */
@Getter
@EqualsAndHashCode
public class Nickname {

  private final String nickname;

  private Nickname(String nickname) {
    this.nickname = nickname;
  }

  public static Nickname of(String rawNickname) {
    return new Nickname(rawNickname);
  }

  @Override
  public String toString() {
    return nickname;
  }
}
