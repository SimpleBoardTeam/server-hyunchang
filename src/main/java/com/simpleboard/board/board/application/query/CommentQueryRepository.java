package com.simpleboard.board.board.application.query;

/**
 * Comment 조회용 저장소 포트.
 *
 * <p>Comment 관련 조회 인터페이스
 *
 * @domain repository-port
 */
public interface CommentQueryRepository {
  CommentListReadModel getCommentList(CommentListCriteria criteria);
}
