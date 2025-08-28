package com.simpleboard.board.board.application.converter;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.*;

import com.simpleboard.board.board.application.dto.response.CommentDetailResult;
import com.simpleboard.board.board.application.exception.CommentNotFoundException;
import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.entity.Comment;
import com.simpleboard.board.board.domain.comment.entity.GuestComment;
import com.simpleboard.board.board.domain.comment.vo.CommentState;
import com.simpleboard.board.board.domain.comment.vo.CommentType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentConverterTest {

  private final CommentResultConverter converter = new CommentResultConverter();

  @Test
  @DisplayName("ACTIVATE MemberComment → CommentDetailResult 매핑")
  void memberComment_mapping() {
    Comment comment = buildComment(CommentType.MEMBER);

    CommentDetailResult result = converter.toCommentDetailResult(comment, "memberNick");

    assertResult(result, comment);
    assertThat(result.nickname()).isEqualTo("memberNick");
  }

  @Test
  @DisplayName("ACTIVATE GuestComment → CommentDetailResult 매핑")
  void guestComment_mapping() {
    Comment comment = buildComment(CommentType.GUEST);

    CommentDetailResult result = converter.toCommentDetailResult(comment, null);

    assertResult(result, comment);
    assertThat(result.nickname()).isEqualTo(((GuestComment) comment).getNickname());
  }

  @Test
  @DisplayName("DELETED 상태 Comment → 최소 정보만 반환")
  void deletedComment_mapping() {
    Comment comment = buildComment(CommentType.GUEST);
    setField(comment, "commentState", CommentState.DELETED);

    CommentDetailResult result = converter.toCommentDetailResult(comment, null);

    assertThat(result.commentId()).isEqualTo(comment.getId());
    assertThat(result.commentType()).isNull();
    assertThat(result.content()).isNull();
  }

  @Test
  @DisplayName("유효하지 않은 상태(ACTIVATE, DELETE 제외 아님) → CommentNotFoundException")
  void invalidState_throws() {
    Comment comment = buildComment(CommentType.MEMBER);
    setField(comment, "commentState", CommentState.COMMENT_ACTIVATE_POST_DELETED);

    assertThatThrownBy(() -> converter.toCommentDetailResult(comment, "x"))
        .isInstanceOf(CommentNotFoundException.class);
  }

  private Comment buildComment(CommentType type) {
    Comment comment;
    if (type == CommentType.GUEST) {
      comment =
          Comment.write(
              CommentCreateParams.builder()
                  .postId(1L)
                  .content("content")
                  .commentType(type)
                  .nickname("guest")
                  .password("pw")
                  .build());
    } else if (type == CommentType.MEMBER) {
      comment =
          Comment.write(
              CommentCreateParams.builder()
                  .postId(1L)
                  .content("content")
                  .commentType(type)
                  .writerId(1001L)
                  .build());
    } else return null;
    setField(comment, "id", 13L);
    setField(comment, "createdAt", LocalDateTime.now());
    return comment;
  }

  private void assertResult(CommentDetailResult result, Comment comment) {
    assertThat(result.commentId()).isEqualTo(comment.getId());
    assertThat(result.content()).isEqualTo(comment.getContent());
    assertThat(result.parentId()).isEqualTo(comment.getParentId());
  }
}
