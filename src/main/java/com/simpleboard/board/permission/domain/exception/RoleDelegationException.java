package com.simpleboard.board.permission.domain.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;
import com.simpleboard.board.permission.domain.RoleName;

public class RoleDelegationException extends ServiceException {
  public static final ErrorCode ERROR_CODE = ErrorCode.ROLE_NOT_ASSIGNED;

  public RoleDelegationException(Long userId, RoleName roleName) {
    super(ERROR_CODE, "userId=" + userId + " 은(는) 역할 " + roleName + " 을(를) 가지고 있지 않아 위임할 수 없습니다.");
  }
}
