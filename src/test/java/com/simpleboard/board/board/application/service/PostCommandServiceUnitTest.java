package com.simpleboard.board.board.application.service;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.board.application.converter.PostCommandParamsConverter;
import com.simpleboard.board.board.application.converter.PostInfoResultConverter;
import com.simpleboard.board.board.application.dto.request.PostCreateCommand;
import com.simpleboard.board.board.application.dto.request.PostDeleteCommand;
import com.simpleboard.board.board.application.dto.request.PostEditCommand;
import com.simpleboard.board.board.application.dto.response.PostCreateResult;
import com.simpleboard.board.board.application.dto.response.PostEditResult;
import com.simpleboard.board.board.application.dto.response.PostToggleLikeResult;
import com.simpleboard.board.board.application.testutil.PostCommandMockRepository;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import com.simpleboard.board.board.domain.post.entity.GuestPost;
import com.simpleboard.board.board.domain.post.entity.MemberPost;
import com.simpleboard.board.board.domain.post.entity.Post;
import com.simpleboard.board.board.domain.post.exception.PostPasswordNotMatchException;
import com.simpleboard.board.board.domain.post.repository.PostCommandRepository;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import com.simpleboard.board.board.domain.testUtil.VisitorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** <b>PostCommandService 클래스 UseCase 테스트 클래스</b> */
class PostCommandServiceUnitTest {

  private final PostCommandRepository postCommandRepository = new PostCommandMockRepository();
  private final PostCommandService postCommandService =
      new PostCommandServiceImpl(
          new PostCommandParamsConverter(), new PostInfoResultConverter(), postCommandRepository);

  private Long memberId1 = 1001L;
  private Long memberId2 = 1002L;
  private String vId1 = "vid1";
  private String vId2 = "vid2";

  @Test
  @DisplayName("GuestPost 생성 테스트")
  void create_GuestPost() {
    Visitor author = VisitorUtil.guest(vId1);
    PostCreateCommand command = getPostCreateCommand(author);

    PostCreateResult result = postCommandService.createPost(author, command);
    Post post =
        postCommandRepository.findPostById(result.postId()).orElseThrow(AssertionError::new);

    assertThat(result.postId()).isNotNull();
    assertThat(post.getId()).isEqualTo(result.postId());
    assertThat(post instanceof GuestPost).isTrue();
  }

  @Test
  @DisplayName("MemberPost 생성 테스트")
  void create_MemberPost() {
    Visitor author = VisitorUtil.member(vId1, memberId1);
    PostCreateCommand command = getPostCreateCommand(author);
    assertThat(command.postType()).isEqualTo(PostTypeEnum.MEMBER);

    PostCreateResult result = postCommandService.createPost(author, command);
    Post post =
        postCommandRepository.findPostById(result.postId()).orElseThrow(AssertionError::new);

    assertThat(result.postId()).isNotNull();
    assertThat(post.getId()).isEqualTo(result.postId());
    assertThat(post instanceof MemberPost).isTrue();
  }

  @Test
  @DisplayName("GuestPost 수정 테스트")
  void edit_GuestPost() {
    Visitor author = VisitorUtil.guest(vId1);
    Long postId = insertPost(author);

    PostEditCommand command =
        PostEditCommand.builder()
            .postId(postId)
            .title("newTitle")
            .content("newContent")
            .postType(PostTypeEnum.GUEST)
            .hashTags("#new1#new2#new3")
            .password("password")
            .build();

    PostEditResult result = postCommandService.editPost(author, command);
    Post post = postCommandRepository.findPostById(postId).orElseThrow(AssertionError::new);

    assertThat(post.getTitle()).isEqualTo(command.title());
    assertThat(post.getContent()).isEqualTo(command.content());
  }

  @Test
  @DisplayName("MemberPost 수정 테스트")
  void edit_MemberPost() {
    Visitor author = VisitorUtil.member(vId2, memberId2);
    Long postId = insertPost(author);

    PostEditCommand command =
        PostEditCommand.builder()
            .postId(postId)
            .title("newTitle")
            .content("newContent")
            .postType(PostTypeEnum.MEMBER)
            .hashTags("#new1#new2#new3")
            .build();

    PostEditResult result = postCommandService.editPost(author, command);
    Post post =
        postCommandRepository.findPostById(result.postId()).orElseThrow(AssertionError::new);

    assertThat(post.getTitle()).isEqualTo(command.title());
    assertThat(post.getContent()).isEqualTo(command.content());
  }

  @Test
  @DisplayName("GuestPost 삭제 테스트")
  void delete_GuestPost() {
    Visitor author = VisitorUtil.guest(vId1);
    Long postId = insertPost(author);

    PostDeleteCommand command =
        PostDeleteCommand.builder()
            .postId(postId)
            .postType(PostTypeEnum.GUEST)
            .password("password")
            .build();

    postCommandService.deletePost(author, command);
    Post post = postCommandRepository.findPostById(postId).orElseThrow(AssertionError::new);

    assertThat(post.getIsDeleted()).isTrue();
  }

  @Test
  @DisplayName("MemberPost 삭제 테스트")
  void delete_MemberPost() {
    Visitor author = VisitorUtil.member(vId1, memberId1);
    Long postId = insertPost(author);

    PostDeleteCommand command =
        PostDeleteCommand.builder().postId(postId).postType(PostTypeEnum.MEMBER).build();

    postCommandService.deletePost(author, command);
    Post post = postCommandRepository.findPostById(postId).orElseThrow(AssertionError::new);

    assertThat(post.getIsDeleted()).isTrue();
  }

  @Test
  @DisplayName("MemberPost 삭제 엣지 테스트")
  void delete_MemberPost_Edge_Test() {
    Visitor author = VisitorUtil.member(vId1, memberId1);
    Long postId = insertPost(author);

    PostDeleteCommand command =
        PostDeleteCommand.builder()
            .postId(postId)
            .postType(PostTypeEnum.GUEST)
            .password("password")
            .build();

    postCommandService.deletePost(author, command);
    Post post = postCommandRepository.findPostById(postId).orElseThrow(AssertionError::new);

    assertThat(post.getIsDeleted()).isTrue();
  }

  @Test
  @DisplayName("GuestPost 삭제 에러 테스트")
  void delete_GuestPost_Error_Test() {
    Visitor author = VisitorUtil.guest(vId1);
    Long postId = insertPost(author);

    PostDeleteCommand command =
        PostDeleteCommand.builder().postId(postId).postType(PostTypeEnum.MEMBER).build();

    assertThatThrownBy(() -> postCommandService.deletePost(author, command))
        .isInstanceOf(PostPasswordNotMatchException.class);
  }

  @Test
  @DisplayName("toggle like 테스트")
  void toggleLike_Test() {
    Long postId = insertPost(VisitorUtil.guest(vId1));

    Visitor visitor1 = VisitorUtil.member(vId1, memberId1);
    Visitor visitor2 = VisitorUtil.member(vId2, memberId2);

    PostToggleLikeResult result1 = postCommandService.toggleLike(visitor1, postId);

    assertThat(result1.isLiked()).isTrue();
    assertThat(result1.likeCount()).isEqualTo(1);

    PostToggleLikeResult result2 = postCommandService.toggleLike(visitor2, postId);

    assertThat(result2.isLiked()).isTrue();
    assertThat(result2.likeCount()).isEqualTo(2);

    PostToggleLikeResult result3 = postCommandService.toggleLike(visitor1, postId);
    assertThat(result3.isLiked()).isFalse();
    assertThat(result3.likeCount()).isEqualTo(1);
  }

  private Long insertPost(Visitor visitor) {
    PostCreateCommand command = getPostCreateCommand(visitor);
    PostCreateResult post = postCommandService.createPost(visitor, command);
    return post.postId();
  }

  private PostCreateCommand getPostCreateCommand(Visitor visitor) {
    PostCreateCommand.PostCreateCommandBuilder builder =
        PostCreateCommand.builder()
            .boardId(1L)
            .boardName("first board")
            .title("title")
            .content("content")
            .hashTags("t1 t2 t3");

    if (visitor.type().equals(VisitorType.GUEST))
      return builder.postType(PostTypeEnum.GUEST).nickname("nickname").password("password").build();
    else if (visitor.type().equals(VisitorType.MEMBER))
      return builder.postType(PostTypeEnum.MEMBER).authorId(visitor.memberId()).build();
    return builder.build();
  }
}
