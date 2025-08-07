package com.simpleboard.board.board.domain.post.dto;

import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import lombok.Builder;

/**
 * Post edit 요청 모델.
 *
 * <p>Req: app -> domain
 *
 * @domain request-dto
 */
@Builder
public record PostEditParams(
    String title, String content, String hashTags, PostTypeEnum type, String password) {}
