package com.simpleboard.board.permission.infrastructure.repository;

import com.simpleboard.board.permission.domain.repository.RoleCatalog;
import com.simpleboard.board.permission.domain.vo.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class InMemoryRoleCatalog implements RoleCatalog {

  private final Map<RoleName, Role> cache;

  public InMemoryRoleCatalog() {
    EnumMap<RoleName, Role> map = new EnumMap<>(RoleName.class);

    map.put(RoleName.BOARD_ADMIN,
        Role.of(RoleName.BOARD_ADMIN, Set.of(
            Permission.CREATE_BOARD, Permission.DELETE_BOARD)));


    this.cache = Map.copyOf(map);
  }

  @Override
  public Role get(RoleName roleName) {
    Role role = cache.get(roleName);
    if (role == null) {
      throw new IllegalArgumentException("정의되지 않은 RoleName: " + roleName);
    }
    return role;
  }
}