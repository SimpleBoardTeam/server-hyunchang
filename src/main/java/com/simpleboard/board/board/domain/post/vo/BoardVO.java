package com.simpleboard.board.board.domain.post.vo;

/**
 * <b>Board</b> Value Object.
 *
 * <p>Post가 속해있는 Board에 대한 정보
 *
 * @domain value-object
 * @since 1.0
 */
public record BoardVO(Long boardId) {
  public static BoardVO of(Long boardId) {
    return new BoardVO(boardId);
  }
}
