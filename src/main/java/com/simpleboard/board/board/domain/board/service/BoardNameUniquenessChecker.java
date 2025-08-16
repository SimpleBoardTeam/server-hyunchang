package com.simpleboard.board.board.domain.board.service;

import com.simpleboard.board.board.domain.board.exception.DuplicateBoardNameException;
import com.simpleboard.board.board.domain.board.vo.BoardName;

public interface BoardNameUniquenessChecker {
  boolean isUnique(BoardName name);

  default void ensureUnique(BoardName name) {
    if (!isUnique(name)) {
      throw new DuplicateBoardNameException("이미 존재하는 보드명: " + name);
    }
  }
}
