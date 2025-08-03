package com.simpleboard.board.board.domain.testUtil;

import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.vo.CommentType;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;

/**
 * <b>CommentCreateParams 빌더 클래스</b>
 *
 * <p>CommentCreateParams를 쉽게 생성하도록 도와주는 builder>
 */
public class CommentCreateParamsBuilder {
  Long commentId = 1L;
  Long postId = 100L;
  Long parentCommentId = null;
  String content = "comment";
  CommentType commentType;
  Long writerId;
  String nickname;
  String password;

  public static CommentCreateParamsBuilder builder(Visitor visitor) {
    if (visitor.type() == VisitorType.GUEST) return new CommentCreateParamsBuilder().guest(visitor);
    if (visitor.type() == VisitorType.MEMBER)
      return new CommentCreateParamsBuilder().member(visitor);
    return null;
  }

  public CommentCreateParamsBuilder guest(Visitor visitor) {
    this.commentType = CommentType.GUEST;
    this.writerId = null;
    this.nickname = "nickname";
    this.password = "password";
    return this;
  }

  public CommentCreateParamsBuilder member(Visitor visitor) {
    this.commentType = CommentType.MEMBER;
    this.writerId = visitor.memberId();
    this.nickname = null;
    this.password = null;
    return this;
  }

  public CommentCreateParamsBuilder commentId(Long commentId) {
    this.commentId = commentId;
    return this;
  }

  public CommentCreateParamsBuilder postId(Long postId) {
    this.postId = postId;
    return this;
  }

  public CommentCreateParamsBuilder parentCommentId(Long parentCommentId) {
    this.parentCommentId = parentCommentId;
    return this;
  }

  public CommentCreateParamsBuilder content(String content) {
    this.content = content;
    return this;
  }

  public CommentCreateParamsBuilder commentType(CommentType commentType) {
    this.commentType = commentType;
    return this;
  }

  public CommentCreateParamsBuilder nickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public CommentCreateParamsBuilder password(String password) {
    this.password = password;
    return this;
  }

  public CommentCreateParams build() {
    return new CommentCreateParams(
        postId, parentCommentId, content, commentType, writerId, nickname, password);
  }
}
