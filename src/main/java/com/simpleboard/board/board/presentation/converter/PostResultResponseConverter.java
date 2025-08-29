package com.simpleboard.board.board.presentation.converter;

import com.simpleboard.board.board.application.dto.response.PostDetailsQueryResult;
import com.simpleboard.board.board.presentation.dto.response.PostDetailsResponse;
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
}
