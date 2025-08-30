package com.simpleboard.board.board.presentation.dto.response;

import com.simpleboard.board.board.domain.comment.vo.CommentType;
import java.time.LocalDateTime;
import lombok.Builder;

/**
 * Comment 정보 응답 모델.
 *
 * <p>Res: Presentation <- Client
 *
 * @domain response-dto
 */
@Builder
public record CommentDetailResponse(
    Boolean isDeleted,
    Long commentId,
    Long parentId,
    CommentType commentType,
    String content,
    String nickname,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
