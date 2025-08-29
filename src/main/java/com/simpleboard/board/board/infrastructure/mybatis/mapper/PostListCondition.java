package com.simpleboard.board.board.infrastructure.mybatis.mapper;

import lombok.Builder;

/**
 * <b>Post 목록 조회 Condition</b>
 *
 * <p>Req: repository -> mapper
 *
 * @domain request-dto
 */
@Builder
public record PostListCondition(
    Long boardId,
    int limit,
    int offset,
    String searchType, // TITLE|NICKNAME|HASHTAG|CONTENT 또는 null
    String keyword // null 가능
    ) {}
