package com.simpleboard.board.member.domain;

import java.util.Objects;
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
public class Nickname {

  private final String value;

  private Nickname(String value) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("닉네임은 비어있을 수 없습니다.");
    }
    this.value = value.trim();
  }

  public static Nickname of(String raw) {
    return new Nickname(raw);
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Nickname nickname)) return false;
    return Objects.equals(value, nickname.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
