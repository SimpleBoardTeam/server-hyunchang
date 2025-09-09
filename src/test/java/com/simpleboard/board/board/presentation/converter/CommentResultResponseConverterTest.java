package com.simpleboard.board.board.presentation.converter;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.board.application.dto.response.CommentDetailResult;
import com.simpleboard.board.board.domain.comment.vo.CommentType;
import com.simpleboard.board.board.presentation.dto.response.CommentDetailResponse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentResultResponseConverterTest {

  private final CommentResultResponseConverter converter = new CommentResultResponseConverter();

  @Test
  @DisplayName("일반 댓글(삭제되지 않음): Result -> Response 매핑")
  void toResponse_active() {
    // given
    LocalDateTime created = LocalDateTime.now().minusMinutes(10);
    LocalDateTime updated = LocalDateTime.now();
    CommentDetailResult result =
        CommentDetailResult.builder()
            .isDeleted(false)
            .commentId(101L)
            .parentId(11L)
            .commentType(CommentType.MEMBER)
            .content("정상 댓글입니다")
            .nickname("회원닉")
            .createdAt(created)
            .updatedAt(updated)
            .build();

    // when
    CommentDetailResponse response = converter.toResponse(result);

    // then
    assertThat(response.isDeleted()).isFalse();
    assertThat(response.commentId()).isEqualTo(101L);
    assertThat(response.parentId()).isEqualTo(11L);
    assertThat(response.commentType()).isEqualTo(CommentType.MEMBER);
    assertThat(response.content()).isEqualTo("정상 댓글입니다");
    assertThat(response.nickname()).isEqualTo("회원닉");
    assertThat(response.createdAt()).isEqualTo(created);
    assertThat(response.updatedAt()).isEqualTo(updated);
  }

  @Test
  @DisplayName("삭제된 댓글: Result -> Response 매핑 (내용/닉네임/updatedAt null 가능)")
  void toResponse_deleted() {
    // given
    LocalDateTime created = LocalDateTime.now().minusDays(1);
    CommentDetailResult result =
        CommentDetailResult.builder()
            .isDeleted(true)
            .commentId(202L)
            .parentId(null)
            .commentType(null) // 삭제 응답에선 타입/내용/닉네임 모두 null
            .content(null)
            .nickname(null)
            .createdAt(created)
            .updatedAt(null)
            .build();

    // when
    CommentDetailResponse response = converter.toResponse(result);

    // then
    assertThat(response.isDeleted()).isTrue();
    assertThat(response.commentId()).isEqualTo(202L);
    assertThat(response.parentId()).isNull();
    assertThat(response.commentType()).isNull();
    assertThat(response.content()).isNull();
    assertThat(response.nickname()).isNull();
    assertThat(response.createdAt()).isEqualTo(created);
    assertThat(response.updatedAt()).isNull();
  }
}
