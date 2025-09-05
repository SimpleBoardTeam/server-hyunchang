package com.simpleboard.board.board.application.testutil;

import com.simpleboard.board.board.domain.board.entity.Board;
import com.simpleboard.board.board.domain.board.repository.BoardRepository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class BoardCommandMockRepository implements BoardRepository {

  private final Map<Long, Board> store = new HashMap<>();
  private final AtomicLong sequence = new AtomicLong(0);

  @Override
  public Board save(Board board) {
    Board persisted = board;
    Long id = board.getBoardId();
    if (id == null) {
      id = sequence.incrementAndGet();
      persisted =
          Board.reconstruct(id, board.getBoardName(), board.getManager(), board.getCreatedAt());
    }
    store.put(id, persisted);
    return persisted;
  }

  @Override
  public Optional<Board> findByBoardName(String boardName) {
    return store.values().stream()
        .filter(board -> board.getBoardName().toString().equals(boardName))
        .findFirst();
  }

  @Override
  public Optional<Board> findById(Long boardId) {
    return Optional.ofNullable(store.get(boardId));
  }

  @Override
  public boolean existsByNameNormalized(String normalizedName) {
    return store.values().stream()
        .anyMatch(board -> board.getBoardName().normalized().equals(normalizedName));
  }

  @Override
  public void deleteById(Long boardId) {
    store.remove(boardId);
  }
}
