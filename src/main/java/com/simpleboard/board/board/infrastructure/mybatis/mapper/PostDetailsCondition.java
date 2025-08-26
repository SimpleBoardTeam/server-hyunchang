package com.simpleboard.board.board.infrastructure.mybatis.mapper;

import com.simpleboard.board.board.domain.common.vo.VisitorType;
import lombok.Builder;

/**
 * <b>Post 상세조회 조건 record</b>
 */
@Builder
public record PostDetailsCondition(
    Long postId, String vid, Long memberId, VisitorType visitorType) {}
