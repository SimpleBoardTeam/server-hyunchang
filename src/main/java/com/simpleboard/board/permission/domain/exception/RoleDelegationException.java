package com.simpleboard.board.permission.domain.exception;

import com.simpleboard.board.permission.domain.RoleName;

public class RoleDelegationException extends RuntimeException {
  public RoleDelegationException(Long userId, RoleName roleName) {
    super("userId=" + userId + " 은(는) 역할 " + roleName + " 을(를) 가지고 있지 않아 위임할 수 없습니다.");
  }
}
