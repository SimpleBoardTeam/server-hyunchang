package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.dto.request.PostDetailsQuery;
import com.simpleboard.board.board.application.dto.response.PostDetailsQueryResult;

/**
 * <b>Post 조회 유스케이스</b>
 *
 * <p>Post 상세조회, 목록조회 수행
 *
 * @domain application-service
 * @transactional
 */
public interface PostQueryService {
  PostDetailsQueryResult getPostDetails(PostDetailsQuery query);
}
