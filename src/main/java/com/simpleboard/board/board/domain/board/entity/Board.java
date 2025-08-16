package com.simpleboard.board.board.domain.board.entity;

import com.simpleboard.board.board.domain.board.service.BoardNameUniquenessChecker;
import com.simpleboard.board.board.domain.board.vo.BoardName;
import com.simpleboard.board.board.domain.board.vo.Manager;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * <b>Board</b> Aggregate Root.
 *
 * <p>Aggregate 역할 설명
 *
 * <p>포함 엔티티
 *
 * <ul>
 *   <li>엔티티 목록
 * </ul>
 *
 * @domain aggregate-root
 * @see 연관 Service 이름
 * @since 1.0
 */
@Getter
public class Board {

  private final Long boardId;
  private final BoardName boardName;
  private final Manager manager;
  private final LocalDateTime createdAt;

  private Board(Long boardId, BoardName boardName, Manager manager, LocalDateTime createdAt) {
    this.boardId = boardId;
    this.boardName = boardName;
    this.manager = manager;
    this.createdAt = createdAt;
  }

  public static Board create(
      BoardName boardName, Visitor visitor, BoardNameUniquenessChecker checker) {
    checker.ensureUnique(boardName);
    return new Board(null, boardName, Manager.of(visitor.memberId()), LocalDateTime.now());
  }

  public Board withId(Long id) {
    return new Board(id, this.boardName, this.manager, this.createdAt);
  }
}
