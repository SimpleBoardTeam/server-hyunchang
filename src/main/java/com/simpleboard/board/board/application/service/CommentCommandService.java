package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.dto.request.CommentCreateCommand;
import com.simpleboard.board.board.application.dto.request.CommentDeleteCommand;
import com.simpleboard.board.board.application.dto.response.CommentDetailResult;
import com.simpleboard.board.board.application.exception.CommentNotFoundException;
import com.simpleboard.board.board.domain.common.vo.Visitor;

/**
 * Comment 생성, 삭제 유스케이스.
 *
 * @domain application-service
 * @transactional
 */
public interface CommentCommandService {
  /**
   * <b>Comment 생성 메서드</b>
   *
   * <p>Comment를 생성 후, 생성한 Comment에 대한 상세 응답을 반환
   *
   * @since 1.0
   */
  public CommentDetailResult createComment(Visitor visitor, CommentCreateCommand command);

  /**
   * <b>Comment 삭제 메서드</b>
   *
   * <p>Comment 삭제 권한에 대한 검증 후 soft deletion 적용
   *
   * @throws CommentNotFoundException
   * @since 1.0
   */
  public CommentDetailResult deleteComment(Visitor visitor, CommentDeleteCommand command);
}
