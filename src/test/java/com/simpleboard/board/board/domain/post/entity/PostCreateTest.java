package com.simpleboard.board.board.domain.post.entity;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.CreateParams;
import com.simpleboard.board.board.domain.post.testutil.PostCreateParamsBuilder;
import com.simpleboard.board.board.domain.post.testutil.VisitorUtil;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <b>Post 생성 테스트 클래스</b>
 *
 * <p>Post 생성과 관련된 테스트 수행
 */
class PostCreateTest {

  @Test
  @DisplayName("GuestPost 생성 타입 테스트")
  void guestPost_Type_Test() {
    // given
    Visitor guestVisitor = VisitorUtil.guest("vid");
    CreateParams params = PostCreateParamsBuilder.builder(guestVisitor).build();

    // when
    Post post = Post.write(params);
    GuestPost guestPost = (GuestPost) post;

    // then
    assertThat(post).isNotNull();
    assertThat(post).isInstanceOf(GuestPost.class);

    assertThat(post.getBoard().boardId()).isEqualTo(params.boardId());
    assertThat(post.getBoard().boardName()).isEqualTo(params.boardName());
    assertThat(post.getTitle()).isEqualTo(params.title());
    assertThat(post.getContent()).isEqualTo(params.content());
    assertThat(post.getIsDeleted()).isFalse();

    assertThat(guestPost.getNickname()).isEqualTo(params.nickname());
    assertThat(guestPost.getPassword()).isEqualTo(params.password());
  }

  @Test
  @DisplayName("MemberPost 생성 타입 테스트")
  void memberPost_Type_Test() {
    // given
    Visitor memberVisitor = VisitorUtil.member("vid", 1L);
    CreateParams params = PostCreateParamsBuilder.builder(memberVisitor).build();

    // when
    Post post = Post.write(params);
    MemberPost memberPost = (MemberPost) post;

    // then
    assertThat(post).isNotNull();
    assertThat(post).isInstanceOf(MemberPost.class);

    assertThat(post.getBoard().boardId()).isEqualTo(params.boardId());
    assertThat(post.getBoard().boardName()).isEqualTo(params.boardName());
    assertThat(post.getTitle()).isEqualTo(params.title());
    assertThat(post.getContent()).isEqualTo(params.content());
    assertThat(post.getIsDeleted()).isFalse();

    assertThat(memberPost.getMemberId()).isEqualTo(memberVisitor.memberId());
  }

  @Test
  @DisplayName("HashTag 생성 테스트")
  void hashTag_Create_Test() {
    List<String> tagList = List.of("tag1", "tag2", "tag3", "tag4", "tag5");
    Visitor visitor = VisitorUtil.guest("vid");
    CreateParams params =
        PostCreateParamsBuilder.builder(visitor).hashTags("tag1 tag2,tag3#tag4 # # tag5").build();

    List<HashTag> tags = Post.write(params).getTags().tags();

    assertThat(tags.size()).isEqualTo(5);
    for (int i = 0; i < 5; i++) assertThat(tags.get(i).getTag()).isEqualTo(tagList.get(i));
  }
}
