package com.simpleboard.board.board.domain.board.vo;

/**
 * <b>Manager</b> Value Object.
 *
 * <p>보드의 관리자를 식별하는 책임을 가지며, 유효한 memberId만을 허용한다.
 *
 * @domain value-object
 * @since 1.0
 */
public record Manager(Long memberId) {

  public Manager {
    if (memberId == null || memberId <= 0) {
      throw new IllegalArgumentException("Manager.memberId는 양수여야 합니다.");
    }
  }

  public static Manager of(Long memberId) {
    return new Manager(memberId);
  }
}
