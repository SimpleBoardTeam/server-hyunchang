package com.simpleboard.board.board.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Comment 생성 요청 모델.
 *
 * <p>Req: Client -> Presentation
 *
 * @domain request-dto
 */
public record CommentCreateForm(
    Long parentId,
    @NotBlank Long postId,
    @NotBlank @Size(min = 2, max = 500) String content,
    @Size(min = 2, max = 16) String nickname,
    @Size(min = 4, max = 16) String password) {}
