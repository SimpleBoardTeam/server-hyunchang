package com.simpleboard.board.board.infrastructure.jpa.repository;

import com.simpleboard.board.board.infrastructure.jpa.entity.BoardEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardEntityRepository extends JpaRepository<BoardEntity, Long> {
  Optional<BoardEntity> findByBoardName(String boardName);
}
