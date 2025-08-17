package com.simpleboard.board.permission.infrastructure.mapper;

import com.simpleboard.board.permission.domain.entity.PermissionPolicy;
import com.simpleboard.board.permission.infrastructure.entity.PermissionPolicyEntity;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * <b>PermissionPolicy Domain Entity <-> JPA Entity Mapping 클래스</b>
 *
 * <p> PermissionPolicy 도메인과 엔티티 간 맵핑
 */
@Component
@RequiredArgsConstructor
public class PermissionPolicyMapper {

  private final ManagerAssignmentMapper assignmentMapper;

  public PermissionPolicy toDomain(PermissionPolicyEntity e) {
    var assigns = e.getManagerAssignments().stream().map(assignmentMapper::toDomain).toList();
    return PermissionPolicy.of(e.getBoardId(), assigns);
  }

  public PermissionPolicyEntity toEntity(PermissionPolicy d) {
    var entities = d.getManagerAssignments().stream().map(assignmentMapper::toEntity).toList();
    return new PermissionPolicyEntity(d.getBoardId(), new ArrayList<>(entities));
  }
}
