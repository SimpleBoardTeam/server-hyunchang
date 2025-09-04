package com.simpleboard.board.board.application.converter;

import com.simpleboard.board.board.application.dto.request.CommentCreateCommand;
import com.simpleboard.board.board.application.dto.request.CommentDeleteCommand;
import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.dto.CommentDeleteParams;
import org.springframework.stereotype.Component;

/**
 * <b>Comment Request converter 클래스</b>
 *
 * <p>Comment B.C의 Command -> Params 로 변환
 *
 * <p>application -> domain
 */
@Component
public class CommentCommandParamsConverter {

  public CommentCreateParams toCreateParams(CommentCreateCommand command) {
    return CommentCreateParams.builder()
        .postId(command.postId())
        .commentType(command.commentType())
        .parentCommentId(command.parentCommentId())
        .content(command.content())
        .nickname(command.nickname())
        .password(command.password())
        .writerId(command.writerId())
        .build();
  }

  public CommentDeleteParams toDeleteParams(CommentDeleteCommand command) {
    return CommentDeleteParams.builder().password(command.password()).build();
  }
}
