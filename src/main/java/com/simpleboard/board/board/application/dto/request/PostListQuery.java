package com.simpleboard.board.board.application.dto.request;

import lombok.Builder;

/**
 * <b>Post 목록 조회 요청 모델</b>
 *
 * <p>Req: presentation -> application
 *
 * @domain request-dto
 */
@Builder
public record PostListQuery(
    Long boardId,
    int page,
    int size,
    String searchType, // title|nickname|hashtag|content 또는 null
    String keyword // null 가능
    ) {}
