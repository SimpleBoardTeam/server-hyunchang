package com.simpleboard.board.permission.infrastructure.repository.query;

import com.simpleboard.board.permission.application.query.repository.PermissionQueryRepository;
import com.simpleboard.board.permission.domain.entity.PermissionPolicy;
import com.simpleboard.board.permission.infrastructure.entity.PermissionPolicyEntity;
import com.simpleboard.board.permission.infrastructure.mapper.PermissionPolicyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PermissionQueryRepositoryImpl implements PermissionQueryRepository {

  private final PermissionQueryJpaRepository jpaRepository;
  private final PermissionPolicyMapper permissionPolicyMapper;

  @Override
  public PermissionPolicy getByBoardId(Long boardId) {
    PermissionPolicyEntity entity =
        jpaRepository
            .findById(boardId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "해당 boardId의 Permission Policy가 존재하지 않습니다: " + boardId));
    return permissionPolicyMapper.toDomain(entity);
  }
}
