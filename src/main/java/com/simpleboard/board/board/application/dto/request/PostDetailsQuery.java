package com.simpleboard.board.board.application.dto.request;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import lombok.Builder;

/**
 * <b>Post 상세 조회 요청 모델</b>
 *
 * <p>Req: presentation -> application
 *
 * @domain request-dto
 */
@Builder
public record PostDetailsQuery(Visitor visitor, Long postId) {}
