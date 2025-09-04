package com.simpleboard.board.board.presentation.converter;

import com.simpleboard.board.board.application.dto.request.CommentCreateCommand;
import com.simpleboard.board.board.application.dto.request.CommentCreateCommand.CommentCreateCommandBuilder;
import com.simpleboard.board.board.application.dto.request.CommentDeleteCommand;
import com.simpleboard.board.board.application.dto.request.CommentDeleteCommand.CommentDeleteCommandBuilder;
import com.simpleboard.board.board.domain.comment.vo.CommentType;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import com.simpleboard.board.board.presentation.dto.request.CommentCreateForm;
import com.simpleboard.board.board.presentation.dto.request.CommentDeleteForm;
import org.springframework.stereotype.Component;

/**
 * <b>Comment Form to Command 컨버터 클래스</b>
 *
 * <p>Form DTO -> Command DTO로 변환을 담당한다.
 */
@Component
public class CommentFormCommandConverter {

  public CommentCreateCommand toCommentCreateCommand(CommentCreateForm form, Visitor visitor) {
    CommentCreateCommandBuilder builder =
        CommentCreateCommand.builder()
            .parentCommentId(form.parentId())
            .postId(form.postId())
            .content(form.content());
    if (visitor.type().equals(VisitorType.MEMBER)) {
      return builder.commentType(CommentType.MEMBER).writerId(visitor.memberId()).build();
    } else if (visitor.type().equals(VisitorType.GUEST)) {
      return builder
          .commentType(CommentType.GUEST)
          .nickname(form.nickname())
          .password(form.password())
          .build();
    }
    throw new IllegalArgumentException();
  }

  public CommentDeleteCommand toCommentDeleteCommand(
      Long commentId, CommentDeleteForm form, Visitor visitor) {
    CommentDeleteCommandBuilder builder = CommentDeleteCommand.builder().commentId(commentId);
    if (visitor.type().equals(VisitorType.MEMBER)) {
      return builder.build();
    } else if (visitor.type().equals(VisitorType.GUEST)) {
      return builder.password(form.password()).build();
    }
    throw new IllegalArgumentException();
  }
}
