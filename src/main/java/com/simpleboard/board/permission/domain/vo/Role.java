package com.simpleboard.board.permission.domain.vo;

import java.util.Set;
import lombok.Getter;

/**
 * <b>Role</b> Value Object.
 *
 * <p>역할명과 권한셋을 가지는 불변 VO
 *
 * @domain value-object
 * @since 1.0
 */
@Getter
public final class Role {

  private final RoleName roleName;
  private final Set<Permission> permissions;

  private Role(RoleName roleName, Set<Permission> permissions) {
    this.roleName = roleName;
    this.permissions = Set.copyOf(permissions);
  }

  public static Role of(RoleName roleName, Set<Permission> permissions) {
    return new Role(roleName, permissions);
  }

  public boolean hasPermission(Permission permission) {
    return permission != null && permissions.contains(permission);
  }

  public boolean isSameRole(Role other) {
    return this.roleName == other.getRoleName();
  }
}
