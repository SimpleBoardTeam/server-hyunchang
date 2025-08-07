package com.simpleboard.board.permission.infrastructure.repository.query;

import com.simpleboard.board.permission.application.query.repository.PermissionQueryRepository;
import com.simpleboard.board.permission.domain.PermissionPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PermissionQueryRepositoryImpl implements PermissionQueryRepository {

  private final PermissionQueryJpaRepository jpaRepository;

  @Override
  public PermissionPolicy getByBoardId(Long boardId) {
    return jpaRepository
        .findByBoardId(boardId)
        .orElseThrow(() -> new IllegalArgumentException("해당 boardId의 권한 정책이 존재하지 않습니다: " + boardId))
        .toDomain();
  }
}
