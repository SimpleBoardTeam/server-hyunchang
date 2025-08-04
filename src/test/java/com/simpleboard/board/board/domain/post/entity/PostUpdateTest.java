package com.simpleboard.board.board.domain.post.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.PostCreateParams;
import com.simpleboard.board.board.domain.post.dto.PostEditParams;
import com.simpleboard.board.board.domain.post.exception.MemberPostPermissionException;
import com.simpleboard.board.board.domain.post.exception.PostPasswordNotMatchException;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import com.simpleboard.board.board.domain.testUtil.PostCreateParamsBuilder;
import com.simpleboard.board.board.domain.testUtil.VisitorUtil;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <b>Post 수정 테스트 클래스</b>
 *
 * <p>Post의 수정 및 관련 권한 테스트 수행
 */
public class PostUpdateTest {

  private Long memberId = 1001L;
  private Long otherMemberId = 1002L;

  @Test
  @DisplayName("GuestPost update title,content 수정 테스트")
  public void guestPost_Update_Succeed_Test() {
    Visitor visitor = VisitorUtil.guest("vid");
    PostCreateParams params = PostCreateParamsBuilder.builder(visitor).password("password").build();

    Post guestPost = Post.write(params);

    PostEditParams postEditParams =
        new PostEditParams("edited title", "edited content", "", PostTypeEnum.GUEST, "password");

    guestPost.editPost(visitor, postEditParams);
    assertThat(guestPost.getTitle()).isEqualTo("edited title");
    assertThat(guestPost.getContent()).isEqualTo("edited content");
  }

  @Test
  @DisplayName("GuestPost update 비밀번호 불일치 실패테스트")
  public void guestPost_Update_Fail_Test() {
    Visitor visitor = VisitorUtil.guest("vid");
    PostCreateParams params = PostCreateParamsBuilder.builder(visitor).password("password").build();

    Post guestPost = Post.write(params);

    PostEditParams postEditParams = new PostEditParams("", "", "", PostTypeEnum.GUEST, "wrong pw");

    assertThatThrownBy(() -> guestPost.editPost(visitor, postEditParams))
        .isInstanceOf(PostPasswordNotMatchException.class);
  }

  @Test
  @DisplayName("MemberPost update title,content 수정 테스트")
  public void memberPost_Update_Succeed_Test() {
    Visitor visitor = VisitorUtil.member("vid", memberId);
    Visitor reqVisitor = VisitorUtil.member("vid2", memberId); // 같은 memberId, 다른 vid
    PostCreateParams params = PostCreateParamsBuilder.builder(visitor).build();

    Post memberPost = Post.write(params);
    PostEditParams postEditParams = getEditParamsMember("");

    memberPost.editPost(reqVisitor, postEditParams);
    assertThat(memberPost.getTitle()).isEqualTo("edited title");
    assertThat(memberPost.getContent()).isEqualTo("edited content");
  }

  @Test
  @DisplayName("MemberPost update 실패 테스트")
  public void memberPost_Update_Fail_Test() {
    Visitor visitor = VisitorUtil.member("vid", memberId);
    PostCreateParams params = PostCreateParamsBuilder.builder(visitor).build();
    Post memberPost = Post.write(params);

    Visitor guestVisitor = VisitorUtil.guest("vid");
    Visitor diffMemberVisitor = VisitorUtil.member("vid2", otherMemberId);
    PostEditParams postEditParams = getEditParamsMember("");

    assertThatThrownBy(() -> memberPost.editPost(guestVisitor, postEditParams))
        .isInstanceOf(MemberPostPermissionException.class);
    assertThatThrownBy(() -> memberPost.editPost(diffMemberVisitor, postEditParams))
        .isInstanceOf(MemberPostPermissionException.class);
  }

  @Test
  @DisplayName("Post update HashTag 수정 테스트")
  public void MemberPost_HashTag_Update_Succeed_Test() {
    List<String> tagList = List.of("Tag1", "Tag2", "Tag3");

    Visitor visitor = VisitorUtil.member("vid", memberId);
    PostCreateParams params =
        PostCreateParamsBuilder.builder(visitor).hashTags("tag1#tag2#tag3#tag4").build();
    Post post = Post.write(params);

    PostEditParams postEditParams = getEditParamsMember("Tag1#Tag2#Tag3");

    post.editPost(visitor, postEditParams);

    assertThat(post.getTags().tags().size()).isEqualTo(3);

    for (int i = 0; i < 3; i++)
      assertThat(post.getTags().tags().get(i).getTag()).isEqualTo(tagList.get(i));
  }

  private static PostEditParams getEditParamsMember(String hashtag) {
    return new PostEditParams("edited title", "edited content", hashtag, PostTypeEnum.MEMBER, "");
  }
}
