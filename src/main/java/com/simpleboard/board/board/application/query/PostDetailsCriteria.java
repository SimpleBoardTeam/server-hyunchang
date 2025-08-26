package com.simpleboard.board.board.application.query;

import com.simpleboard.board.board.domain.common.vo.VisitorType;
import lombok.Builder;

/**
 * Post 상세 조회 조건 DTO
 *
 * <p>Req: service -> respository
 *
 * @domain request-dto
 */
@Builder
public record PostDetailsCriteria(VisitorType type, String vId, Long memberId, Long postId) {}
