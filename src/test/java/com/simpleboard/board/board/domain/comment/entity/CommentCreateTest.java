package com.simpleboard.board.board.domain.comment.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.vo.CommentState;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.testUtil.CommentCreateParamsBuilder;
import com.simpleboard.board.board.domain.testUtil.VisitorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <b>Comment 생성 테스트 클래스</b>
 *
 * <p>다양한 Comment의 생성 테스트 수행
 */
class CommentCreateTest {

  @Test
  @DisplayName("GuestComment 생성 테스트")
  void guestComment_Create_Success_Test() {
    Visitor visitor = VisitorUtil.guest("vid");
    CommentCreateParams params = CommentCreateParamsBuilder.builder(visitor).build();

    Comment comment = Comment.write(params);

    assertThat(comment).isInstanceOf(GuestComment.class);
    GuestComment guestComment = (GuestComment) comment;

    assertThat(params.content()).isEqualTo(comment.getContent());
    assertThat(params.parentCommentId()).isEqualTo(comment.getParentId());
    assertThat(comment.getCommentState()).isEqualTo(CommentState.ACTIVATE);

    assertThat(params.nickname()).isEqualTo(guestComment.getNickname());
    assertThat(params.password()).isEqualTo(guestComment.getPassword());
  }

  @Test
  @DisplayName("MemberComment 생성 테스트")
  void memberComment_Create_Success_Test() {
    Visitor visitor = VisitorUtil.member("vid", 1L);
    CommentCreateParams params = CommentCreateParamsBuilder.builder(visitor).build();

    Comment comment = Comment.write(params);

    assertThat(comment).isInstanceOf(MemberComment.class);
    MemberComment memberComment = (MemberComment) comment;

    assertThat(params.content()).isEqualTo(comment.getContent());
    assertThat(params.parentCommentId()).isEqualTo(comment.getParentId());
    assertThat(comment.getCommentState()).isEqualTo(CommentState.ACTIVATE);

    assertThat(params.writerId()).isEqualTo(memberComment.getWriterId());
  }

  @Test
  @DisplayName("대댓글 생성 테스트")
  void recomment_Create_Success_Test() {
    Visitor writer = VisitorUtil.guest("");
    CommentCreateParams params =
        CommentCreateParamsBuilder.builder(writer).parentCommentId(100L).build();
    Comment comment = Comment.write(params);

    assertThat(comment.getParentId()).isEqualTo(100L);
  }
}
