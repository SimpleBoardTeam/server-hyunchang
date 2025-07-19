package com.simpleboard.board.permission.domain;

import lombok.Getter;

@Getter
public class ManagerAssignment {
  private final Long boardId;

  private final Long userId;
  private final Role role;

  private ManagerAssignment(Long boardId, Long userId, Role role) {
    this.boardId = boardId;
    this.userId = userId;
    this.role = role;
  }

  public static ManagerAssignment create(Long boardId, Long userId, RoleName roleName) {
    return new ManagerAssignment(boardId, userId, Role.of(roleName));
  }

  public boolean hasPermission(Permission permission) {
    return role.hasPermission(permission);
  }

  public boolean hasRole(RoleName roleName) {
    return role.hasSameRole(roleName);
  }

  public boolean isOwnedBy(Long userId) {
    return userId.equals(this.userId);
  }

  public RoleName getRoleName() {
    return role.getRoleName();
  }
}
