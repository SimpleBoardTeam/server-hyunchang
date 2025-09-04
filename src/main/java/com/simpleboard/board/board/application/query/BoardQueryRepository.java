package com.simpleboard.board.board.application.query;

import java.util.Optional;

public interface BoardQueryRepository {
  Optional<String> findBoardNameById(Long boardId);
}
