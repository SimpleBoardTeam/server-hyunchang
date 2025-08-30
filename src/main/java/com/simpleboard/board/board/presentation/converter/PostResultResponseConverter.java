package com.simpleboard.board.board.presentation.converter;

import com.simpleboard.board.board.application.dto.response.PostDetailsQueryResult;
import com.simpleboard.board.board.application.dto.response.PostListQueryResult;
import com.simpleboard.board.board.presentation.dto.response.PostDetailsResponse;
import com.simpleboard.board.board.presentation.dto.response.PostListResponse;
import org.springframework.stereotype.Component;

/**
 * <b>Post Result to Response 컨버터 클래스</b>
 *
 * <p>Result DTO -> Response DTO로 변환을 담당한다.
 *
 * <p>application -> presentation
 */
@Component
public class PostResultResponseConverter {

  public PostDetailsResponse toResponse(PostDetailsQueryResult result) {
    return PostDetailsResponse.builder()
        .postId(result.postId())
        .postType(result.postType())
        .title(result.title())
        .content(result.content())
        .createdAt(result.createdAt())
        .updatedAt(result.updatedAt())
        .hashTags(result.hashTags())
        .viewCount(result.viewCount())
        .likeCount(result.likeCount())
        .isLiked(result.isLiked())
        .authorId(result.authorId())
        .authorNickname(result.authorNickname())
        .build();
  }

  public PostListResponse toResponse(PostListQueryResult result) {
    return PostListResponse.builder()
        .boardId(result.boardId())
        .boardName(result.boardName())
        .totalPosts(result.totalPosts())
        .totalComments(result.totalComments())
        .page(result.page())
        .size(result.size())
        .posts(result.posts().stream().map(this::toResponse).toList())
        .build();
  }

  public PostListResponse.PostSummary toResponse(PostListQueryResult.PostSummary summary) {
    return PostListResponse.PostSummary.builder()
        .postId(summary.postId())
        .title(summary.title())
        .nickname(summary.nickname())
        .createdAt(summary.createdAt())
        .views(summary.views())
        .commentCount(summary.commentCount())
        .likeCount(summary.likeCount())
        .build();
  }
}
