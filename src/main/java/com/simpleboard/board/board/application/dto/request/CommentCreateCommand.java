package com.simpleboard.board.board.application.dto.request;

import com.simpleboard.board.board.domain.comment.vo.CommentType;
import lombok.Builder;

/**
 * Comment 생성 요청 모델.
 *
 * <p>Req: presentation -> application
 *
 * @domain request-dto
 */
@Builder
public record CommentCreateCommand(
    Long postId,
    Long parentCommentId,
    String content,
    CommentType commentType,
    Long writerId,
    String nickname,
    String password) {}
