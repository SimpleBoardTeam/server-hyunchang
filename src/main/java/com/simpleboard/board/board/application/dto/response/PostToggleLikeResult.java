package com.simpleboard.board.board.application.dto.response;

import lombok.Builder;

@Builder
public record PostToggleLikeResult(Boolean isLiked, Integer likeCount) {}
