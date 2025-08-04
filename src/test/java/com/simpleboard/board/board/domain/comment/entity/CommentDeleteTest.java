package com.simpleboard.board.board.domain.comment.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.dto.CommentDeleteParams;
import com.simpleboard.board.board.domain.comment.exception.CommentPasswordException;
import com.simpleboard.board.board.domain.comment.exception.MemberCommentPermissionException;
import com.simpleboard.board.board.domain.comment.vo.CommentState;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.testUtil.CommentCreateParamsBuilder;
import com.simpleboard.board.board.domain.testUtil.VisitorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <b>Comment 삭제 테스트 클래스</b>
 *
 * <p>다양한 타입의 Comment 삭제 테스트 수행
 */
public class CommentDeleteTest {

  @Test
  @DisplayName("GuestComment 삭제 성공 테스트")
  void guestComment_Delete_Success_Test() {
    Visitor writer = VisitorUtil.guest("vid");
    Visitor memberVisitor = VisitorUtil.member("vid", 100L);
    CommentCreateParams createParams =
        CommentCreateParamsBuilder.builder(writer).password("pw").build();
    CommentDeleteParams deleteParams = new CommentDeleteParams("pw");

    Comment comment1 = Comment.write(createParams);
    Comment comment2 = Comment.write(createParams);
    comment1.deleteComment(writer, deleteParams);
    comment2.deleteComment(memberVisitor, deleteParams);

    assertThat(comment1.getCommentState()).isEqualTo(CommentState.DELETED);
    assertThat(comment2.getCommentState()).isEqualTo(CommentState.DELETED);
  }

  @Test
  @DisplayName("GuestComment 삭제 실패 테스트")
  void guestComment_Delete_Fail_Test() {
    Visitor writer = VisitorUtil.guest("vid");
    Visitor guestVisitor = VisitorUtil.guest("vid");
    Visitor memberVisitor = VisitorUtil.member("vid", 100L);

    CommentCreateParams createParams =
        CommentCreateParamsBuilder.builder(writer).password("pw").build();
    CommentDeleteParams deleteParams = new CommentDeleteParams("wrong pw");

    Comment comment1 = Comment.write(createParams);
    Comment comment2 = Comment.write(createParams);

    assertThatThrownBy(() -> comment1.deleteComment(guestVisitor, deleteParams))
        .isInstanceOf(CommentPasswordException.class);
    assertThatThrownBy(() -> comment2.deleteComment(memberVisitor, deleteParams))
        .isInstanceOf(CommentPasswordException.class);
  }

  @Test
  @DisplayName("MemberComment 삭제 성공 테스트")
  void memberComment_Delete_Success_Test() {
    Visitor writer = VisitorUtil.member("vid", 100L);
    Visitor memberVisitor = VisitorUtil.member("vid_vid", 100L);
    CommentCreateParams createParams = CommentCreateParamsBuilder.builder(writer).build();
    Comment comment = Comment.write(createParams);
    CommentDeleteParams deleteParams = new CommentDeleteParams("");

    comment.deleteComment(memberVisitor, deleteParams);

    assertThat(comment.getCommentState()).isEqualTo(CommentState.DELETED);
  }

  @Test
  @DisplayName("MemberComment 삭제 실패 테스트")
  void memberComment_Delete_Fail_Test() {
    Visitor writer = VisitorUtil.member("vid", 100L);
    Visitor memberVisitor = VisitorUtil.member("vid_vid", 1001L);
    Visitor guestVisitor = VisitorUtil.guest("vid");
    CommentCreateParams createParams1 = CommentCreateParamsBuilder.builder(writer).build();
    CommentCreateParams createParams2 =
        CommentCreateParamsBuilder.builder(writer).password("pw").build();
    Comment comment1 = Comment.write(createParams1);
    Comment comment2 = Comment.write(createParams2);

    CommentDeleteParams deleteParams1 = new CommentDeleteParams("");
    CommentDeleteParams deleteParams2 = new CommentDeleteParams("pw");

    assertThatThrownBy(() -> comment1.deleteComment(memberVisitor, deleteParams1))
        .isInstanceOf(MemberCommentPermissionException.class);
    assertThatThrownBy(() -> comment2.deleteComment(memberVisitor, deleteParams2))
        .isInstanceOf(MemberCommentPermissionException.class);
    assertThatThrownBy(() -> comment1.deleteComment(guestVisitor, deleteParams1))
        .isInstanceOf(MemberCommentPermissionException.class);
    assertThatThrownBy(() -> comment2.deleteComment(guestVisitor, deleteParams2))
        .isInstanceOf(MemberCommentPermissionException.class);
  }
}
