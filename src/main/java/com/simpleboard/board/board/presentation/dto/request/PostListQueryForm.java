package com.simpleboard.board.board.presentation.dto.request;

import com.simpleboard.board.board.presentation.util.SearchType;

/**
 * Post 목록 조회 요청 모델.
 *
 * <p>Req: Client -> Presentation
 *
 * @domain request-dto
 */
public record PostListQueryForm(
    Integer page,
    Integer size,
    SearchType searchType, // null 허용
    String keyword) {}
