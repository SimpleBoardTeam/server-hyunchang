package com.simpleboard.board.board.domain.comment.dto;

import lombok.Builder;

/**
 * Comment 삭제 요청 모델.
 *
 * <p>Req: application -> domain
 *
 * @domain request-dto
 */
@Builder
public record CommentDeleteParams(String password) {}
