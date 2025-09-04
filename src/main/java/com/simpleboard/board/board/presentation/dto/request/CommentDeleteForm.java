package com.simpleboard.board.board.presentation.dto.request;

import jakarta.validation.constraints.Size;

/**
 * Comment 삭제 요청 모델.
 *
 * <p>Req: Client -> Presentation
 *
 * @domain request-dto
 */
public record CommentDeleteForm(@Size(min = 4, max = 16) String password) {}
