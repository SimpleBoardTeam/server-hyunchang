package com.simpleboard.board.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

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
