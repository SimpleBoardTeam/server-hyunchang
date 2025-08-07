package com.simpleboard.board.permission.infrastructure.repository.command;

import com.simpleboard.board.permission.infrastructure.entity.PermissionPolicyEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionCommandJpaRepository
    extends JpaRepository<PermissionPolicyEntity, Long> {
  Optional<PermissionPolicyEntity> findByBoardId(Long boardId);
}
