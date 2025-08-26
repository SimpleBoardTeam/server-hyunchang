package com.simpleboard.board.board.application.dto.request;

import lombok.Builder;

/**
 * Board 생성 요청 모델.
 *
 * <p>Req: Presentation -> application
 *
 * @domain request-dto
 */
@Builder
public record BoardCreateCommand(String boardName) {}
