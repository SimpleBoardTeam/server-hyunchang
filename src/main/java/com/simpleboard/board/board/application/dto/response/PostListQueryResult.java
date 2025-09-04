package com.simpleboard.board.board.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

/**
 * <b>Post 목록 조회 응답 모델</b>
 *
 * <p>Res: presentation <- application
 *
 * @domain response-dto
 */
@Builder
public record PostListQueryResult(
    Long boardId,
    String boardName,
    long totalPosts,
    long totalComments,
    int page,
    int size,
    List<PostSummary> posts) {
  @Builder
  public record PostSummary(
      Long postId,
      String title,
      String nickname,
      LocalDateTime createdAt,
      long views,
      int commentCount,
      int likeCount) {}
}
