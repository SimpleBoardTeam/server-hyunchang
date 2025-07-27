package com.simpleboard.board.board.domain.post.dto;

/**
 * Toggle like, get Post detail 응답 모델.
 *
 * <p>Res: app <- domain
 *
 * @domain response-dto
 */
public record LikeInfo(Boolean isLiked, Integer likeCount) {}
