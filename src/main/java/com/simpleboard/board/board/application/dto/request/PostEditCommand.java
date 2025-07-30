package com.simpleboard.board.board.application.dto.request;

import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import lombok.Builder;

/**
 * Post 수정 요청 모델.
 *
 * <p>Req: Presentation -> application
 *
 * @domain request-dto
 */
@Builder
public record PostEditCommand(
    Long postId,
    PostTypeEnum postType,
    String password,
    String title,
    String content,
    String hashTags) {}
