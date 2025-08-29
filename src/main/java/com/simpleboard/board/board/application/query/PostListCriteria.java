package com.simpleboard.board.board.application.query;

import lombok.Builder;

/**
 * Post 목록 조회 조건 DTO
 *
 * <p>Req: service -> repository
 *
 * @domain request-dto
 */
@Builder
public record PostListCriteria(
    Long boardId,
    String boardName,
    int page,
    int size,
    String searchType, // title|author|hashtag|content 또는 null
    String keyword // null 가능
    ) {}
