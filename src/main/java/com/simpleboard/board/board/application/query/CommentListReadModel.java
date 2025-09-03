package com.simpleboard.board.board.application.query;

import lombok.Builder;

/**
 * Comment 목록 조회 반환 DTO
 *
 * <p>Res: service <- repository
 *
 * @domain response-dto
 */
@Builder
public record CommentListReadModel() {}
