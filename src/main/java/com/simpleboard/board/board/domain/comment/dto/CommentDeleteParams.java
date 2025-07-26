package com.simpleboard.board.board.domain.comment.dto;

/**
 * Comment 삭제 요청 모델.
 *
 * <p>Req: application -> domain
 *
 * @domain request-dto
 */
public record CommentDeleteParams(String password) {}
