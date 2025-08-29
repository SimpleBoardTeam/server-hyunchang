package com.simpleboard.board.board.application.query;

/**
 * Post 조회용 저장소 포트.
 *
 * <p>Post 관련 조회 인터페이스
 *
 * @domain repository-port
 */
public interface PostQueryRepository {

  PostDetailsReadModel getPostDetails(PostDetailsCriteria criteria);

  PostListReadModel getPostList(PostListCriteria criteria);
}
