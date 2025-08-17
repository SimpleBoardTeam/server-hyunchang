package com.simpleboard.board.permission.infrastructure.repository.command;

import com.simpleboard.board.permission.domain.entity.PermissionPolicy;
import com.simpleboard.board.permission.domain.repository.PermissionRepository;
import com.simpleboard.board.permission.infrastructure.entity.PermissionPolicyEntity;
import com.simpleboard.board.permission.infrastructure.mapper.PermissionPolicyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {

  private final PermissionCommandJpaRepository jpaRepository;
  private final PermissionPolicyMapper permissionPolicyMapper;

  @Override
  public PermissionPolicy getByBoardId(Long boardId) {
    PermissionPolicyEntity entity =
        jpaRepository
            .findByBoardId(boardId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "해당 boardId의 Permission Policy가 존재하지 않습니다: " + boardId));
    return permissionPolicyMapper.toDomain(entity);
  }

  @Override
  public void delete(PermissionPolicy permissionPolicy) {
    jpaRepository.deleteById(permissionPolicy.getBoardId());
  }

  @Override
  public PermissionPolicy save(PermissionPolicy permissionPolicy) {
    PermissionPolicyEntity toSave = permissionPolicyMapper.toEntity(permissionPolicy);
    PermissionPolicyEntity saved = jpaRepository.save(toSave);
    return permissionPolicyMapper.toDomain(saved);
  }
}
