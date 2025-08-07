package com.simpleboard.board.board.domain.post.dto;

import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import lombok.Builder;

/**
 * Post create 요청 모델.
 *
 * <p>Req: app -> domain
 *
 * @domain request-dto
 */
@Builder
public record PostCreateParams(
    Long boardId,
    String boardName,
    String title,
    String content,
    String hashTags,
    PostTypeEnum type,
    Long authorId,
    String nickname,
    String password) {}
