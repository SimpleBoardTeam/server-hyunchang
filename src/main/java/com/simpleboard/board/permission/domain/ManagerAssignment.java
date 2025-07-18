package com.simpleboard.board.permission.domain;

public class ManagerAssignment {

  private final Long userId;
  private final Role role;

  private ManagerAssignment(Long userId, Role role) {
    this.userId = userId;
    this.role = role;
  }

  public static ManagerAssignment create(Long userId, RoleName roleName) {
    return new ManagerAssignment(userId, Role.of(roleName));
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
}