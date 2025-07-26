package com.simpleboard.board.board.domain.post.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.CreateParams;
import com.simpleboard.board.board.domain.post.dto.DeleteParams;
import com.simpleboard.board.board.domain.post.exception.MemberPostPermissionException;
import com.simpleboard.board.board.domain.post.exception.PostPasswordNotMatchException;
import com.simpleboard.board.board.domain.post.testutil.PostCreateParamsBuilder;
import com.simpleboard.board.board.domain.post.testutil.VisitorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <b>Post 삭제 테스트 클래스</b>
 *
 * <p>Post 삭제시 검증, soft deletion에 관한 테스트 수행
 */
public class PostDeleteTest {

  @Test
  @DisplayName("GuestPost 삭제 성공 테스트")
  void guestPost_Deletion_Succeed_Test() {
    Visitor visitor = VisitorUtil.guest("vid");
    Visitor diffVisitor = VisitorUtil.guest("vid2");
    CreateParams params = PostCreateParamsBuilder.builder(visitor).password("password").build();

    Post post = Post.write(params);

    DeleteParams deleteParams = new DeleteParams("password");
    post.deletePost(diffVisitor, deleteParams);

    assertThat(post.getIsDeleted()).isTrue();
  }

  @Test
  @DisplayName("GuestPost 삭제 실페 테스트")
  void guestPost_Deletion_Fail_Test() {
    Visitor visitor = VisitorUtil.guest("vid");
    Visitor diffVisitor = VisitorUtil.guest("vid2");
    CreateParams params = PostCreateParamsBuilder.builder(visitor).build();
    Post post = Post.write(params);

    DeleteParams deleteParams = new DeleteParams("wrong p.w");
    assertThatThrownBy(() -> post.deletePost(diffVisitor, deleteParams))
        .isInstanceOf(PostPasswordNotMatchException.class);
  }

  @Test
  @DisplayName("MemberPost 삭제 성공 테스트")
  void memberPost_Deletion_Succeed_Test() {
    Visitor visitor = VisitorUtil.member("vid", 1L);
    Visitor diffVisitor = VisitorUtil.member("vid2", 1L);
    CreateParams params = PostCreateParamsBuilder.builder(visitor).build();
    Post post = Post.write(params);

    DeleteParams deleteParams = new DeleteParams("");
    post.deletePost(diffVisitor, deleteParams);
    assertThat(post.getIsDeleted()).isTrue();
  }

  @Test
  @DisplayName("MemberPost 삭제 실패 테스트")
  void memberPost_Deletion_Fail_Test() {
    Visitor visitor = VisitorUtil.member("vid", 1L);
    Visitor diffVisitor = VisitorUtil.member("vid1", 2L);
    CreateParams params = PostCreateParamsBuilder.builder(visitor).build();
    Post post = Post.write(params);

    DeleteParams deleteParams = new DeleteParams("");

    assertThatThrownBy(() -> post.deletePost(diffVisitor, deleteParams))
        .isInstanceOf(MemberPostPermissionException.class);
  }
}
