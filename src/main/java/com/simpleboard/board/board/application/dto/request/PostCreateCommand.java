package com.simpleboard.board.board.application.dto.request;

import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import lombok.Builder;

/**
 * Post 생성 요청 모델.
 *
 * <p>Req: Presentation -> application
 *
 * @domain request-dto
 */
@Builder
public record PostCreateCommand(
    Long boardId,
    String boardName,
    String title,
    String content,
    String hashTags,
    PostTypeEnum postType,
    Long authorId,
    String nickname,
    String password) {}
