package com.simpleboard.board.permission.presentation.dto;

import com.simpleboard.board.permission.domain.RoleName;

public record DelegateRoleForm(Long fromUserId, String toUserNickname, RoleName roleType) {}
