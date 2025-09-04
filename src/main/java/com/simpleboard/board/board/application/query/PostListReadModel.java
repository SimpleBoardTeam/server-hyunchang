package com.simpleboard.board.board.application.query;

import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

/**
 * Post 목록 조회 반환 DTO
 *
 * <p>Res: repository <- service
 *
 * @domain response-dto
 */
@Builder
public record PostListReadModel(
    long totalPosts, long totalComments, int page, int size, List<PostSummary> posts) {
  @Builder
  public record PostSummary(
      Long postId,
      String title,
      PostTypeEnum postType,
      Long authorId,
      String nickname,
      LocalDateTime createdAt,
      long views,
      int commentCount,
      int likeCount) {}
}
