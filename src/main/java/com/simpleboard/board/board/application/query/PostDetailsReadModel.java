package com.simpleboard.board.board.application.query;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

/**
 * Post 상세 조회 반환 DTO
 *
 * <p>Res: respotiroy <- service
 *
 * @domain response-dto
 */
@Builder
public record PostDetailsReadModel(
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
    String nickname) {}
