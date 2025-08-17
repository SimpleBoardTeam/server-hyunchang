package com.simpleboard.board.permission.domain.repository;

import com.simpleboard.board.permission.domain.vo.Role;
import com.simpleboard.board.permission.domain.vo.RoleName;

public interface RoleCatalog {
  Role get(RoleName roleName);
}
