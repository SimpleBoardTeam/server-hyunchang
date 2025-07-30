package com.simpleboard.board.board.application.dto.request;

import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import lombok.Builder;

/**
 * Post 삭제 요청 모델.
 *
 * <p>Req: Presentation -> application</p>
 *
 * @domain request-dto
 */
@Builder
public record PostDeleteCommand(Long postId, PostTypeEnum postType, String password) {}
