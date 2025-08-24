package com.simpleboard.board.board.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Post 생성 요청 모델.
 *
 * <p>Req: Client -> Presentation
 *
 * @domain request-dto
 */
public record PostCreateForm(
    @NotBlank Long boardId,
    @NotBlank @Size(min = 2, max = 100) String title,
    @NotBlank @Size(min = 5, max = 10000) String content,
    @Size(min = 2, max = 55) String hashtags,
    @Size(min = 2, max = 16) String nickname,
    @Size(min = 4, max = 255) String password) {}
