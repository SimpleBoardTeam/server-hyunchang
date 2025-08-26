package com.simpleboard.board.board.infrastructure.mybatis.mapper;

import java.time.LocalDateTime;

/**
 * <b>Post 상세 조회 반환 DTO</b>
 */
public record PostDetailsData(
    Long postId,
    String postType,
    String title,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long viewCount,
    Integer likeCount,
    Boolean isLiked) {}
