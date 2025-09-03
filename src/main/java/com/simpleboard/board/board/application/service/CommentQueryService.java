package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.dto.request.CommentListQuery;
import com.simpleboard.board.board.application.dto.response.CommentListQueryResult;

/**
 * <b>Comment 조회 유스케이스</b>
 *
 * <p>Comment 목록조회 수행(페이징 처리)
 *
 * @domain application-service
 * @transactional
 */
public interface CommentQueryService {
  CommentListQueryResult getCommentList(CommentListQuery query);
}
