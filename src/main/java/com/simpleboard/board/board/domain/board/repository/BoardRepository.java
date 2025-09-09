package com.simpleboard.board.board.domain.board.repository;

import com.simpleboard.board.board.domain.board.entity.Board;
import java.util.Optional;

public interface BoardRepository {

  Board save(Board board);

  Optional<Board> findByBoardName(String boardName);

  Optional<Board> findById(Long boardId);

  boolean existsByNameNormalized(String normalizedName);

  void deleteById(Long boardId);
}
