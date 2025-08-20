package com.simpleboard.board.board.presentation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Post 삭제 요청 모델.
 *
 * <p>Req: Client -> Presentation
 *
 * @domain request-dto
 */
@Builder
public record PostDeleteForm(@Size(min = 4, max = 16) String password) {}
