package com.simpleboard.board.permission.presentation.converter;

import com.simpleboard.board.permission.application.command.dto.DelegateRoleCommand;
import com.simpleboard.board.permission.presentation.dto.DelegateRoleForm;

public class DelegateRoleConverter {

  private DelegateRoleConverter() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static DelegateRoleCommand toCommand(DelegateRoleForm form) {
    return new DelegateRoleCommand(form.fromUserId(), form.toUserNickname(), form.roleType());
  }
}
