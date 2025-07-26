package com.simpleboard.board.board.domain.common.vo;

/**
 * <b>Visitor</b> Value Object.
 *
 * <p>서비스 사용자의 정보
 *
 * @domain value-object
 * @since 1.0
 */
public record Visitor(VisitorType type, String vId, Long memberId) {}
