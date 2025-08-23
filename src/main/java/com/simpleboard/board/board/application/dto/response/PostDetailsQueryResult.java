package com.simpleboard.board.board.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

/**
 * <b>Post 단건 조회 응답 모델</b>
 *
 * <p>Res: presentation <- application
 *
 * @domain response-dto
 */
@Builder
public record PostDetailsQueryResult(
    Long postId,
    String postType,
    String title,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    List<String> hashTags,
    Long viewCount,
    Integer likeCount,
    Boolean isLiked,
    Long authorId,
    String authorNickname) {}
