package com.simpleboard.board.board.domain.comment.dto;

import com.simpleboard.board.board.domain.comment.vo.CommentType;
import lombok.Builder;

/**
 * Comment 생성 요청 모델.
 *
 * <p>Req: application -> domain
 *
 * @domain request-dto
 */
@Builder
public record CommentCreateParams(
    Long postId,
    Long parentCommentId,
    String content,
    CommentType commentType,
    Long writerId,
    String nickname,
    String password) {}
