package com.simpleboard.board.board.infrastructure.jpa.mapper;

import com.simpleboard.board.board.domain.board.entity.Board;
import com.simpleboard.board.board.domain.board.vo.BoardName;
import com.simpleboard.board.board.domain.board.vo.Manager;
import com.simpleboard.board.board.infrastructure.jpa.entity.BoardEntity;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {

  private BoardMapper() {}

  /** Domain -> JPA Entity */
  public BoardEntity toEntity(Board domain) {
    return new BoardEntity(
        domain.getBoardId(),
        domain.getBoardName().toString(), // BoardName → String
        domain.getBoardName().normalized(), // BoardName -> String(Normalized)
        domain.getManager().memberId() // Manager → Long
        );
  }

  /** JPA Entity -> Domain */
  public Board toDomain(BoardEntity entity) {
    return Board.reconstruct(
        entity.getBoardId(),
        BoardName.of(entity.getBoardName()), // String → BoardName
        Manager.of(entity.getManagerId()), // Long → Manager
        entity.getCreatedAt());
  }
}
