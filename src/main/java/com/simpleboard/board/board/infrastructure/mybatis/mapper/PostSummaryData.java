package com.simpleboard.board.board.infrastructure.mybatis.mapper;

import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;

import java.time.LocalDateTime;

/**
 * <b>Post 목록 조회 응답 모델</b>
 *
 * <p>Post 한개에 대한 메타 정보를 담은 DTO
 *
 * <p>Res: repository <- mapper
 *
 * @domain response-dto
 */
public record PostSummaryData(
    Long postId,
    PostTypeEnum postType,
    String title,
    Long authorId,
    String nickname, // member post에서 null
    Long views,
    Integer commentCount,
    Integer likeCount,
    LocalDateTime createdAt) {}
