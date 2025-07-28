package com.simpleboard.board.permission.infrastructure.repository.command;

import com.simpleboard.board.permission.application.command.repository.PermissionCommandRepository;
import com.simpleboard.board.permission.domain.PermissionPolicy;
import com.simpleboard.board.permission.infrastructure.entity.PermissionPolicyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PermissionCommandRepositoryImpl implements PermissionCommandRepository {

  private final PermissionCommandJpaRepository jpaRepository;

  @Override
  public PermissionPolicy getByBoardId(Long boardId) {
    return jpaRepository
        .findByBoardId(boardId)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "해당 boardId의 Permission Policy가 존재하지 않습니다: " + boardId))
        .toDomain();
  }

  @Override
  public void delete(PermissionPolicy permissionPolicy) {
    jpaRepository.delete(PermissionPolicyEntity.from(permissionPolicy));
  }

  @Override
  public PermissionPolicy save(PermissionPolicy permissionPolicy) {
    return jpaRepository.save(PermissionPolicyEntity.from(permissionPolicy)).toDomain();
  }
}
