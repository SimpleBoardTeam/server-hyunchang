package com.simpleboard.board.board.application.dto.request;

import lombok.Builder;

/**
 * Comment 삭제 요청 모델.
 *
 * <p>Req: presentation -> application
 *
 * @domain request-dto
 */
@Builder
public record CommentDeleteCommand(Long commentId, String password) {}
