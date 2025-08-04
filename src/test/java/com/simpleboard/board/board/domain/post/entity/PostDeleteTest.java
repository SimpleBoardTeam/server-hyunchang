package com.simpleboard.board.board.domain.post.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.PostCreateParams;
import com.simpleboard.board.board.domain.post.dto.PostDeleteParams;
import com.simpleboard.board.board.domain.post.exception.MemberPostPermissionException;
import com.simpleboard.board.board.domain.post.exception.PostPasswordNotMatchException;
import com.simpleboard.board.board.domain.testUtil.PostCreateParamsBuilder;
import com.simpleboard.board.board.domain.testUtil.VisitorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <b>Post 삭제 테스트 클래스</b>
 *
 * <p>Post 삭제시 검증, soft deletion에 관한 테스트 수행
 */
public class PostDeleteTest {

  private Long memberId = 1001L;
  private Long otherMemberId = 1002L;
  private Visitor guest1 = VisitorUtil.guest("vid");
  private Visitor guest2 = VisitorUtil.guest("vid2");

  @Test
  @DisplayName("GuestPost 삭제 성공 테스트")
  void guestPost_Deletion_Succeed_Test() {
    PostCreateParams params = PostCreateParamsBuilder.builder(guest1).password("password").build();

    Post post = Post.write(params);

    PostDeleteParams postDeleteParams = new PostDeleteParams("password");
    post.deletePost(guest2, postDeleteParams);

    assertThat(post.getIsDeleted()).isTrue();
  }

  @Test
  @DisplayName("GuestPost 삭제 실페 테스트")
  void guestPost_Deletion_Fail_Test() {
    PostCreateParams params = PostCreateParamsBuilder.builder(guest1).build();
    Post post = Post.write(params);
    PostDeleteParams postDeleteParams = new PostDeleteParams("wrong p.w");

    assertThatThrownBy(() -> post.deletePost(guest2, postDeleteParams))
        .isInstanceOf(PostPasswordNotMatchException.class);
  }

  @Test
  @DisplayName("MemberPost 삭제 성공 테스트")
  void memberPost_Deletion_Succeed_Test() {
    Visitor visitor = VisitorUtil.member("vid", memberId);
    Visitor diffVisitor = VisitorUtil.member("vid2", memberId);
    PostCreateParams params = PostCreateParamsBuilder.builder(visitor).build();
    Post post = Post.write(params);
    PostDeleteParams postDeleteParams = new PostDeleteParams("");

    post.deletePost(diffVisitor, postDeleteParams);

    assertThat(post.getIsDeleted()).isTrue();
  }

  @Test
  @DisplayName("MemberPost 삭제 실패 테스트")
  void memberPost_Deletion_Fail_Test() {
    Visitor visitor = VisitorUtil.member("vid", memberId);
    Visitor diffVisitor = VisitorUtil.member("vid1", otherMemberId);
    PostCreateParams params = PostCreateParamsBuilder.builder(visitor).build();
    Post post = Post.write(params);

    PostDeleteParams postDeleteParams = new PostDeleteParams("");

    assertThatThrownBy(() -> post.deletePost(diffVisitor, postDeleteParams))
        .isInstanceOf(MemberPostPermissionException.class);
  }
}
