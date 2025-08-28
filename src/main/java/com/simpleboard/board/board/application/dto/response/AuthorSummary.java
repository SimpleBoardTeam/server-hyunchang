package com.simpleboard.board.board.application.dto.response;

import lombok.Builder;

/**
 * Post Author 정보 응답 모델.
 *
 * <p>Res: Post query service <- Member B.C
 *
 * @domain response-dto
 */
@Builder
public record AuthorSummary(Long authorId, String nickname) {}
