package com.simpleboard.board.board.application.dto.response;

import com.simpleboard.board.board.domain.comment.vo.CommentType;
import java.time.LocalDateTime;
import lombok.Builder;

/**
 * Comment 상세 내용 조회가 필요한 요청 응답 모델.
 *
 * <p>Res: presentation <- application
 *
 * @domain response-dto
 */
@Builder
public record CommentDetailResult(
    Long commentId,
    Long parentId,
    CommentType commentType, // soft deleted 시  null
    String content,
    String nickname,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
