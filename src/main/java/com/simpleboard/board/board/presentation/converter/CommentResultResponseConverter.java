package com.simpleboard.board.board.presentation.converter;

import com.simpleboard.board.board.application.dto.response.CommentDetailResult;
import com.simpleboard.board.board.presentation.dto.response.CommentDetailResponse;
import org.springframework.stereotype.Component;

/**
 * <b>Comment Result to Response 컨버터 클래스</b>
 *
 * <p>Result DTO -> Response DTO로 변환을 담당한다.
 */
@Component
public class CommentResultResponseConverter {
  public CommentDetailResponse toResponse(CommentDetailResult result) {
    return CommentDetailResponse.builder()
        .isDeleted(result.isDeleted())
        .commentId(result.commentId())
        .parentId(result.parentId())
        .commentType(result.commentType())
        .content(result.content())
        .nickname(result.nickname())
        .createdAt(result.createdAt())
        .updatedAt(result.updatedAt())
        .build();
  }
}
