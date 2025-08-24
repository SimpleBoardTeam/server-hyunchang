package com.simpleboard.board.permission.application.command.dto;

import com.simpleboard.board.permission.domain.vo.RoleName;

/**
 * 권한 위임 요청 모델.
 *
 * <p>Req: presentation 계층 -> application 계층
 *
 * @domain request-dto
 */
public record DelegateRoleCommand(Long fromUserId, String toUserNickname, RoleName roleName) {}
