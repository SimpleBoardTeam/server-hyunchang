package com.simpleboard.board.permission.infrastructure.mapper;

import com.simpleboard.board.permission.domain.entity.ManagerAssignment;
import com.simpleboard.board.permission.domain.repository.RoleCatalog;
import com.simpleboard.board.permission.domain.vo.Role;
import com.simpleboard.board.permission.infrastructure.entity.ManagerAssignmentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManagerAssignmentMapper {

  private final RoleCatalog roleCatalog;

  public ManagerAssignment toDomain(ManagerAssignmentEntity e) {
    Role role = roleCatalog.get(e.getRoleName());
    return ManagerAssignment.create(e.getBoardId(), e.getUserId(), role);
  }

  public ManagerAssignmentEntity toEntity(ManagerAssignment d) {
    return new ManagerAssignmentEntity(
        d.getBoardId(),
        d.getUserId(),
        d.getRoleName()
    );
  }
}
