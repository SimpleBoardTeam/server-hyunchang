package com.simpleboard.board.board.domain.comment.entity;

import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.dto.CommentDeleteParams;
import com.simpleboard.board.board.domain.comment.exception.CommentPasswordException;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * <b>Comment 구현체</b> Aggregate Root.
 *
 * <p>Guest가 작성한 Comment 구현체
 *
 * @domain aggregate-root
 * @see Comment
 * @since 1.0
 */
@Getter
@SuperBuilder
public class GuestComment extends Comment {
  private String nickname;
  private String password;

  public GuestComment(CommentCreateParams params) {
    super(params);
    this.nickname = params.nickname();
    this.password = params.password();
  }

  @Override
  protected void checkPermission(Visitor visitor, CommentDeleteParams params) {
    if (!password.equals(params.password())) throw new CommentPasswordException();
  }
}
