package com.simpleboard.board.board.application.dto.request;

import lombok.Builder;

/**
 * Board 삭제 요청 모델.
 *
 * <p>Req: Presentation -> application
 *
 * @domain request-dto
 */
@Builder
public record BoardDeleteCommand(String boardName) {}
