package com.simpleboard.board.permission.domain.entity;

import com.simpleboard.board.permission.domain.vo.Permission;
import com.simpleboard.board.permission.domain.vo.Role;
import com.simpleboard.board.permission.domain.vo.RoleName;
import lombok.Getter;

/**
 * <b>ManagerAssignment</b> Entity(non‑root)
 *
 * <p>멤버와 역할을 맵핑
 *
 * @domain entity
 * @since 1.0
 */
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

  public static ManagerAssignment create(Long boardId, Long userId, Role role) {
    return new ManagerAssignment(boardId, userId, role);
  }

  public boolean hasPermission(Permission permission) {
    return role.hasPermission(permission);
  }

  public boolean isSameRole(Role role) {
    return role.isSameRole(role);
  }

  public boolean isOwnedBy(Long userId) {
    return userId.equals(this.userId);
  }

  public RoleName getRoleName() {
    return role.getRoleName();
  }
}
