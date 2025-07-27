package com.simpleboard.board.board.domain.post.dto;

import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;

/**
 * Post edit 요청 모델.
 *
 * <p>Req: app -> domain
 *
 * @domain request-dto
 */
public record EditParams(
    String title, String content, String hashTags, PostTypeEnum type, String password) {}
