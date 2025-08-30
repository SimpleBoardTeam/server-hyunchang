package com.simpleboard.board.board.presentation.converter;

import com.simpleboard.board.board.application.dto.request.CommentCreateCommand;
import com.simpleboard.board.board.application.dto.request.CommentDeleteCommand;
import com.simpleboard.board.board.domain.comment.vo.CommentType;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import com.simpleboard.board.board.presentation.dto.request.CommentCreateForm;
import com.simpleboard.board.board.presentation.dto.request.CommentDeleteForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CommentFormCommandConverterTest {

    private final CommentFormCommandConverter converter = new CommentFormCommandConverter();

    @Test
    @DisplayName("MEMBER 방문자: CommentCreateForm -> CommentCreateCommand 변환")
    void toCommentCreateCommand_member() {
        // given
        CommentCreateForm form = new CommentCreateForm(
                10L,           // parentId
                100L,          // postId
                "내용입니다",     // content
                null,          // nickname (멤버는 무시)
                null           // password (멤버는 무시)
        );
        Visitor visitor = Visitor.builder()
                .type(VisitorType.MEMBER)
                .memberId(1L)
                .vId("vid-xxx")
                .build();

        // when
        CommentCreateCommand cmd = converter.toCommentCreateCommand(form, visitor);

        // then
        assertThat(cmd.postId()).isEqualTo(100L);
        assertThat(cmd.parentCommentId()).isEqualTo(10L);
        assertThat(cmd.content()).isEqualTo("내용입니다");
        assertThat(cmd.commentType()).isEqualTo(CommentType.MEMBER);
        assertThat(cmd.writerId()).isEqualTo(1L);
        assertThat(cmd.nickname()).isNull();
        assertThat(cmd.password()).isNull();
    }

    @Test
    @DisplayName("GUEST 방문자: CommentCreateForm -> CommentCreateCommand 변환")
    void toCommentCreateCommand_guest() {
        // given
        CommentCreateForm form = new CommentCreateForm(
                null,          // parentId
                200L,          // postId
                "게스트 댓글",     // content
                "게스트닉",        // nickname
                "abcd1234"     // password
        );
        Visitor visitor = Visitor.builder()
                .type(VisitorType.GUEST)
                .vId("vid-guest")
                .build();

        // when
        CommentCreateCommand cmd = converter.toCommentCreateCommand(form, visitor);

        // then
        assertThat(cmd.postId()).isEqualTo(200L);
        assertThat(cmd.parentCommentId()).isNull();
        assertThat(cmd.content()).isEqualTo("게스트 댓글");
        assertThat(cmd.commentType()).isEqualTo(CommentType.GUEST);
        assertThat(cmd.writerId()).isNull();
        assertThat(cmd.nickname()).isEqualTo("게스트닉");
        assertThat(cmd.password()).isEqualTo("abcd1234");
    }

    @Test
    @DisplayName("지원하지 않는 VisitorType이면 CommentCreate 변환 시 예외 발생")
    void toCommentCreateCommand_unsupportedVisitor() {
        // given
        CommentCreateForm form = new CommentCreateForm(
                1L, 2L, "x", null, null
        );
        Visitor visitor = Visitor.builder()
                .type(VisitorType.MANAGER) // 현재 컨버터는 MEMBER/GUEST만 지원
                .memberId(99L)
                .build();

        // expect
        assertThatThrownBy(() -> converter.toCommentCreateCommand(form, visitor))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("MEMBER 방문자: CommentDeleteForm -> CommentDeleteCommand 변환(비밀번호 불필요)")
    void toCommentDeleteCommand_member() {
        // given
        Long commentId = 123L;
        CommentDeleteForm form = null; // 멤버는 비번 불필요하니 null 전달도 허용
        Visitor visitor = Visitor.builder()
                .type(VisitorType.MEMBER)
                .memberId(1L)
                .build();

        // when
        CommentDeleteCommand cmd = converter.toCommentDeleteCommand(commentId, form, visitor);

        // then
        assertThat(cmd.commentId()).isEqualTo(123L);
        assertThat(cmd.password()).isNull();
    }

    @Test
    @DisplayName("GUEST 방문자: CommentDeleteForm -> CommentDeleteCommand 변환(비밀번호 필요)")
    void toCommentDeleteCommand_guest() {
        // given
        Long commentId = 321L;
        CommentDeleteForm form = new CommentDeleteForm("pw-9999");
        Visitor visitor = Visitor.builder()
                .type(VisitorType.GUEST)
                .vId("vid-1")
                .build();

        // when
        CommentDeleteCommand cmd = converter.toCommentDeleteCommand(commentId, form, visitor);

        // then
        assertThat(cmd.commentId()).isEqualTo(321L);
        assertThat(cmd.password()).isEqualTo("pw-9999");
    }

    @Test
    @DisplayName("지원하지 않는 VisitorType이면 CommentDelete 변환 시 예외 발생")
    void toCommentDeleteCommand_unsupportedVisitor() {
        // given
        Long commentId = 999L;
        CommentDeleteForm form = new CommentDeleteForm("pw");
        Visitor visitor = Visitor.builder()
                .type(VisitorType.MANAGER)
                .memberId(77L)
                .build();

        // expect
        assertThatThrownBy(() -> converter.toCommentDeleteCommand(commentId, form, visitor))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
