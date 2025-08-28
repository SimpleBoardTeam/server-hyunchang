package com.simpleboard.board.board.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.util.ReflectionTestUtils.*;

import com.simpleboard.board.board.application.converter.CommentCommandParamsConverter;
import com.simpleboard.board.board.application.converter.CommentResultConverter;
import com.simpleboard.board.board.application.dto.request.CommentCreateCommand;
import com.simpleboard.board.board.application.dto.request.CommentDeleteCommand;
import com.simpleboard.board.board.application.dto.response.AuthorSummary;
import com.simpleboard.board.board.application.dto.response.CommentDetailResult;
import com.simpleboard.board.board.application.exception.CommentNotFoundException;
import com.simpleboard.board.board.application.exception.PostNotExistsException;
import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.entity.Comment;
import com.simpleboard.board.board.domain.comment.entity.GuestComment;
import com.simpleboard.board.board.domain.comment.entity.MemberComment;
import com.simpleboard.board.board.domain.comment.repository.CommentCommandRepository;
import com.simpleboard.board.board.domain.comment.vo.CommentState;
import com.simpleboard.board.board.domain.comment.vo.CommentType;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.entity.GuestPost;
import com.simpleboard.board.board.domain.post.repository.PostCommandRepository;
import com.simpleboard.board.board.domain.testUtil.VisitorUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentCommandServiceTest {

  /* ========= 공통 상수 ========= */
  private static final Long POST_ID = 1L;
  private static final Long PARENT_ID = 2L;
  private static final Long MEMBER_ID = 1_001L;
  private static final Long MEMBER_COMMENT_ID = 10L;
  private static final Long GUEST_COMMENT_ID = 77L;
  private static final String MEMBER_VID = "mVid";
  private static final String GUEST_VID = "gVid";
  private static final String GUEST_PW = "pw";
  private static final String MEMBER_NICKNAME = "memberNick";
  private static final String GUEST_NICKNAME = "guestNick";

  /* ========= Mock 의존성 ========= */
  private PostCommandRepository postCommandRepository = mock(PostCommandRepository.class);
  private CommentCommandRepository commentRepository = mock(CommentCommandRepository.class);
  private MemberFetchService memberFetchService = mock(MemberFetchService.class);

  /* ========= 실제 구현체 ========= */
  private final CommentCommandParamsConverter paramsConverter = new CommentCommandParamsConverter();
  private final CommentResultConverter commentResultConverter = new CommentResultConverter();

  private CommentCommandService service =
      new CommentCommandServiceImpl(
              postCommandRepository,
          paramsConverter,
              commentResultConverter,
          commentRepository,
          memberFetchService);

  @BeforeEach
  void beforeEach() {
    willAnswer(
            inv -> {
              Comment c = inv.getArgument(0);
              if (c instanceof GuestComment) {
                setField(c, "id", GUEST_COMMENT_ID);
              } else if (c instanceof MemberComment) {
                setField(c, "id", MEMBER_COMMENT_ID);
              }
              setField(c, "createdAt", LocalDateTime.now());
              return c;
            })
        .given(commentRepository)
        .save(any(Comment.class));
  }

  /* ====================================================================== */
  /* =========================== createComment ============================ */
  /* ====================================================================== */

  @Test
  @DisplayName("createComment(): MEMBER 댓글 생성 성공")
  void createComment_member_success() {
    // given
    given(postCommandRepository.findPostById(POST_ID)).willReturn(Optional.of(GuestPost.builder().build()));
    given(memberFetchService.fetchAuthorSummary(MEMBER_ID)).willReturn(AuthorSummary.builder()
            .authorId(MEMBER_ID)
            .nickname(MEMBER_NICKNAME).build());

    Visitor visitor = VisitorUtil.member(MEMBER_VID, MEMBER_ID);

    CommentCreateCommand cmd =
        CommentCreateCommand.builder()
            .postId(POST_ID)
            .parentCommentId(PARENT_ID)
            .content("멤버 댓글")
            .commentType(CommentType.MEMBER)
            .writerId(MEMBER_ID)
            .build();

    // when
    CommentDetailResult result = service.createComment(visitor, cmd);

    // then
    assertResult(result, cmd);
    assertThat(result.commentType()).isEqualTo(CommentType.MEMBER);
    assertThat(result.nickname()).isEqualTo(MEMBER_NICKNAME);
  }

  @Test
  @DisplayName("createComment(): GUEST 댓글 생성 성공")
  void createComment_guest_success() {
    // given
    given(postCommandRepository.findPostById(POST_ID)).willReturn(Optional.of(GuestPost.builder().build()));
    Visitor guestVisitor = VisitorUtil.guest(GUEST_VID);

    CommentCreateCommand command =
        CommentCreateCommand.builder()
            .postId(POST_ID)
            .content("guest comment")
            .commentType(CommentType.GUEST)
            .nickname(GUEST_NICKNAME)
            .password(GUEST_PW)
            .build();

    // when
    CommentDetailResult result = service.createComment(guestVisitor, command);

    // then
    assertResult(result, command);
    assertThat(result.commentType()).isEqualTo(CommentType.GUEST);
    assertThat(result.nickname()).isEqualTo(command.nickname());
  }

  @Test
  @DisplayName("createComment(): 게시글 미존재 → PostNotExistsException")
  void createComment_postNotExist_fail() {
    // given
    given(postCommandRepository.findPostById(POST_ID)).willReturn(Optional.empty());

    Visitor guestVisitor = VisitorUtil.guest(GUEST_VID);

    CommentCreateCommand cmd =
        CommentCreateCommand.builder().postId(POST_ID).commentType(CommentType.GUEST).build();

    // when & then
    assertThatThrownBy(() -> service.createComment(guestVisitor, cmd))
        .isInstanceOf(PostNotExistsException.class);
  }

  @Test
  @DisplayName("deleteComment(): GUEST 댓글 삭제 성공 – 모든 필드 검증")
  void deleteComment_guest_success() {
    // given
    Comment guestSaved = guestSaved(); // 실제 엔티티 준비
    given(commentRepository.findById(GUEST_COMMENT_ID)).willReturn(Optional.of(guestSaved));
    given(commentRepository.save(guestSaved)).willReturn(guestSaved);

    Visitor guest = VisitorUtil.guest(GUEST_VID);
    CommentDeleteCommand cmd =
        CommentDeleteCommand.builder().commentId(GUEST_COMMENT_ID).password(GUEST_PW).build();

    // when
    CommentDetailResult result = service.deleteComment(guest, cmd);

    // then
    assertThat(result.commentId()).isEqualTo(guestSaved.getId());
    assertThat(result.parentId()).isEqualTo(guestSaved.getParentId());
    assertThat(result.commentType()).isNull(); // 삭제 후 null
    assertThat(result.content()).isNull();
    assertThat(result.createdAt()).isNotNull();
    assertThat(guestSaved.getCommentState()).isEqualTo(CommentState.DELETED);
  }

  @Test
  @DisplayName("deleteComment(): 댓글 미존재 → CommentNotFoundException")
  void deleteComment_notFound_fail() {
    // given
    given(commentRepository.findById(MEMBER_COMMENT_ID)).willReturn(Optional.empty());

    Visitor member = VisitorUtil.member(MEMBER_VID, MEMBER_ID);
    CommentDeleteCommand cmd =
        CommentDeleteCommand.builder().commentId(MEMBER_COMMENT_ID).password("x").build();

    // when & then
    assertThatThrownBy(() -> service.deleteComment(member, cmd))
        .isInstanceOf(CommentNotFoundException.class);
  }

  private Comment guestSaved() {
    CommentCreateParams params =
        CommentCreateParams.builder()
            .postId(POST_ID)
            .parentCommentId(PARENT_ID)
            .content("삭제될 게스트 댓글")
            .commentType(CommentType.GUEST)
            .nickname(GUEST_NICKNAME)
            .password(GUEST_PW)
            .build();

    Comment c = Comment.write(params);
    setField(c, "id", GUEST_COMMENT_ID);
    setField(c, "createdAt", LocalDateTime.now());
    return c;
  }

  private void assertResult(CommentDetailResult result, CommentCreateCommand cmd) {
    assertThat(result.commentId()).isNotNull();
    assertThat(result.parentId()).isEqualTo(cmd.parentCommentId());
    assertThat(result.content()).isEqualTo(cmd.content());
    assertThat(result.createdAt()).isNotNull();
  }
}
