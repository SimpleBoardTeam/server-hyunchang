package com.simpleboard.board.board.application.converter;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.board.application.dto.request.CommentCreateCommand;
import com.simpleboard.board.board.application.dto.request.CommentDeleteCommand;
import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.dto.CommentDeleteParams;
import com.simpleboard.board.board.domain.comment.vo.CommentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentCommandParamsConverterTest {

  private final CommentCommandParamsConverter converter = new CommentCommandParamsConverter();

  @Test
  @DisplayName("toCreateParams() 필드 매핑 테스트")
  void toCreateParams_field_mapping_Test() {
    // given
    CommentCreateCommand command =
        CommentCreateCommand.builder()
            .postId(1L)
            .parentCommentId(2L)
            .content("content")
            .commentType(CommentType.MEMBER)
            .writerId(1001L)
            .nickname("nick")
            .password("pw")
            .build();

    // when
    CommentCreateParams params = converter.toCreateParams(command);

    // then
    assertThat(params.postId()).isEqualTo(command.postId());
    assertThat(params.parentCommentId()).isEqualTo(command.parentCommentId());
    assertThat(params.content()).isEqualTo(command.content());
    assertThat(params.commentType()).isEqualTo(command.commentType());
    assertThat(params.writerId()).isEqualTo(command.writerId());
    assertThat(params.nickname()).isEqualTo(command.nickname());
    assertThat(params.password()).isEqualTo(command.password());
  }

  @Test
  @DisplayName("toDeleteParams() 필드 매핑 테스트")
  void toDeleteParams_field_mapping_Test() {
    // given
    CommentDeleteCommand command =
        CommentDeleteCommand.builder().commentId(10L).password("pw").build();

    // when
    CommentDeleteParams params = converter.toDeleteParams(command);

    // then
    assertThat(params.password()).isEqualTo(command.password());
  }
}
