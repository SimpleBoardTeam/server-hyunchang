package com.simpleboard.board.board.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Post 수정 요청 모델.
 *
 * <p>Req: Client -> Presentation
 *
 * @domain request-dto
 */
@Builder
public record PostEditForm(
    @NotBlank @Size(min = 2, max = 100) String title,
    @NotBlank @Size(min = 5, max = 10000) String content,
    @Size(min = 2, max = 55) String hashTags,
    @Size(min = 4, max = 16) String password) {}
