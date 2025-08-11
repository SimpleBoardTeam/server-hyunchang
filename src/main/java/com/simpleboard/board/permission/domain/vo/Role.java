package com.simpleboard.board.permission.domain.vo;

import java.util.*;
import lombok.Getter;

@Getter
public class Role {
  private static final Map<RoleName, Role> ROLE_CACHE =
      Map.of(
          RoleName.BOARD_ADMIN,
          new Role(RoleName.BOARD_ADMIN, Set.of(Permission.DELETE_BOARD, Permission.CREATE_BOARD)));

  private final RoleName roleName;
  private final Set<Permission> permissions;

  private Role(RoleName roleName, Set<Permission> permissions) {
    this.roleName = roleName;
    this.permissions = permissions;
  }

  public static Role getPredefined(RoleName roleName) {
    return ROLE_CACHE.get(roleName);
  }

  public boolean hasPermission(Permission permission) {
    return permissions.contains(permission);
  }

  public boolean hasSameRole(RoleName roleName) {
    return Objects.equals(this.roleName, roleName);
  }
}
