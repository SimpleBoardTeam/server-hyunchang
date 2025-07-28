package com.simpleboard.board.permission.application.command.dto;

import com.simpleboard.board.permission.domain.RoleName;

public record DelegateRoleCommand(Long fromUserId, String toUserNickname, RoleName roleName) {}
