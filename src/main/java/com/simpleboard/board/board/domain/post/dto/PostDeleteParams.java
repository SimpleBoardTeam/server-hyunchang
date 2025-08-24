package com.simpleboard.board.board.domain.post.dto;

import lombok.Builder;

/**
 * Post delete 요청 모델.
 *
 * <p>Req: app -> domain
 *
 * @domain request-dto
 */
@Builder
public record PostDeleteParams(String password) {}
