package com.simpleboard.board.board.application.converter;

import com.simpleboard.board.board.application.dto.response.CommentDetailResult;
import com.simpleboard.board.board.application.dto.response.CommentDetailResult.CommentDetailResultBuilder;
import com.simpleboard.board.board.application.exception.CommentNotFoundException;
import com.simpleboard.board.board.domain.comment.entity.Comment;
import com.simpleboard.board.board.domain.comment.entity.GuestComment;
import com.simpleboard.board.board.domain.comment.entity.MemberComment;
import com.simpleboard.board.board.domain.comment.vo.CommentState;
import com.simpleboard.board.board.domain.comment.vo.CommentType;
import org.springframework.stereotype.Component;

/**
 * <b>Comment Response Converter 클래스</b>
 *
 * <p>domain 로직의 결과를 result로 변환
 */
@Component
public class CommentResultConverter {

  /**
   * <b>단일 Comment 상세조회 DTO 변환 메서드</b>
   *
   * <p>MemberComment일 경우 nickname도 같이 전달,
   *
   * <p>GuestComment일 경우 nickname에 "" 전달
   *
   * @param comment Comment Aggregate
   * @param nickname 작성자(Member) nickname
   * @return 단일 Comment 상세조회 DTO
   */
  public CommentDetailResult toCommentDetailResult(Comment comment, String nickname) {
    if (comment.getCommentState() == CommentState.DELETED) {
      return CommentDetailResult.builder()
          .commentId(comment.getId())
          .parentId(comment.getParentId())
          .createdAt(comment.getCreatedAt())
          .isDeleted(true)
          .build();
    }
    // COMMENT_ACTIVATE_POST_DELETED, COMMENT_DELETED_POST_DELETED 상태시 외부 조회 불가
    if (comment.getCommentState() != CommentState.ACTIVATE) throw new CommentNotFoundException();

    CommentDetailResultBuilder builder =
        CommentDetailResult.builder()
            .commentId(comment.getId())
            .parentId(comment.getParentId())
            .content(comment.getContent())
            .createdAt(comment.getCreatedAt())
            .updatedAt(comment.getUpdatedAt())
            .isDeleted(false);
    if (comment instanceof MemberComment) {
      builder.commentType(CommentType.MEMBER).nickname(nickname);
    } else if (comment instanceof GuestComment) {
      builder.commentType(CommentType.GUEST).nickname(((GuestComment) comment).getNickname());
    } else {
      throw new IllegalArgumentException();
    }
    return builder.build();
  }
}
