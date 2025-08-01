package com.simpleboard.board.auth.domain.token.vo;

import lombok.Builder;

/**
 * <b>TokenPair</b> Value Object.
 *
 * <p>Access/Refresh 토큰 한 쌍
 *
 * @domain value-object
 * @since 1.0
 */
@Builder
public record TokenPair(Token access, Token refresh) {}
