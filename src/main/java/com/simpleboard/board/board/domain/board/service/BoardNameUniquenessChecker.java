package com.simpleboard.board.board.domain.board.service;

import com.simpleboard.board.board.domain.board.exception.DuplicateBoardNameException;
import com.simpleboard.board.board.domain.board.vo.BoardName;

/**
 * <b>BoardNameUniquenessChecker</b> Domain Service.
 *
 * <p>보드명 중복 여부를 확인하는 도메인 서비스
 *
 * @domain domain-service
 * @since 1.0
 */
public interface BoardNameUniquenessChecker {
  boolean isUnique(BoardName name);

  default void ensureUnique(BoardName name) {
    if (!isUnique(name)) {
      throw new DuplicateBoardNameException("이미 존재하는 보드명: " + name);
    }
  }
}
