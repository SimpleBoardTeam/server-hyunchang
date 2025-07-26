package com.simpleboard.board.board.domain.post.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.CreateParams;
import com.simpleboard.board.board.domain.post.dto.EditParams;
import com.simpleboard.board.board.domain.post.exception.MemberPostPermissionException;
import com.simpleboard.board.board.domain.post.exception.PostPasswordNotMatchException;
import com.simpleboard.board.board.domain.post.testutil.PostCreateParamsBuilder;
import com.simpleboard.board.board.domain.post.testutil.VisitorUtil;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <b>Post 수정 테스트 클래스</b>
 *
 * <p>Post의 수정 및 관련 권한 테스트 수행
 */
public class PostUpdateTest {

  @Test
  @DisplayName("GuestPost update title,content 수정 테스트")
  public void guestPost_Update_Succeed_Test() {
    Visitor visitor = VisitorUtil.guest("vid");
    CreateParams params = PostCreateParamsBuilder.builder(visitor).password("password").build();

    Post guestPost = Post.write(params);

    EditParams editParams =
        new EditParams("edited title", "edited content", "", PostTypeEnum.GUEST, "password");

    guestPost.editPost(visitor, editParams);
    assertThat(guestPost.getTitle()).isEqualTo("edited title");
    assertThat(guestPost.getContent()).isEqualTo("edited content");
  }

  @Test
  @DisplayName("GuestPost update 비밀번호 불일치 실패테스트")
  public void guestPost_Update_Fail_Test() {
    Visitor visitor = VisitorUtil.guest("vid");
    CreateParams params = PostCreateParamsBuilder.builder(visitor).password("password").build();

    Post guestPost = Post.write(params);

    EditParams editParams = new EditParams("", "", "", PostTypeEnum.GUEST, "wrong pw");

    assertThatThrownBy(() -> guestPost.editPost(visitor, editParams))
        .isInstanceOf(PostPasswordNotMatchException.class);
  }

  @Test
  @DisplayName("MemberPost update title,content 수정 테스트")
  public void memberPost_Update_Succeed_Test() {
    Visitor visitor = VisitorUtil.member("vid", 1L);
    Visitor reqVisitor = VisitorUtil.member("vid2", 1L); // 같은 memberId, 다른 vid
    CreateParams params = PostCreateParamsBuilder.builder(visitor).build();

    Post memberPost = Post.write(params);
    EditParams editParams = getEditParamsMember("");

    memberPost.editPost(reqVisitor, editParams);
    assertThat(memberPost.getTitle()).isEqualTo("edited title");
    assertThat(memberPost.getContent()).isEqualTo("edited content");
  }

  @Test
  @DisplayName("MemberPost update 실패 테스트")
  public void memberPost_Update_Fail_Test() {
    Visitor visitor = VisitorUtil.member("vid", 1L);
    CreateParams params = PostCreateParamsBuilder.builder(visitor).build();
    Post memberPost = Post.write(params);

    Visitor guestVisitor = VisitorUtil.guest("vid");
    Visitor diffMemberVisitor = VisitorUtil.member("vid2", 1002L);
    EditParams editParams = getEditParamsMember("");

    assertThatThrownBy(() -> memberPost.editPost(guestVisitor, editParams))
        .isInstanceOf(MemberPostPermissionException.class);
    assertThatThrownBy(() -> memberPost.editPost(diffMemberVisitor, editParams))
        .isInstanceOf(MemberPostPermissionException.class);
  }

  @Test
  @DisplayName("Post update HashTag 수정 테스트")
  public void MemberPost_HashTag_Update_Succeed_Test() {
    List<String> tagList = List.of("Tag1", "Tag2", "Tag3");

    Visitor visitor = VisitorUtil.member("vid", 1L);
    CreateParams params =
        PostCreateParamsBuilder.builder(visitor).hashTags("tag1#tag2#tag3#tag4").build();
    Post post = Post.write(params);

    EditParams editParams = getEditParamsMember("Tag1#Tag2#Tag3");

    post.editPost(visitor, editParams);

    assertThat(post.getTags().tags().size()).isEqualTo(3);

    for (int i = 0; i < 3; i++)
      assertThat(post.getTags().tags().get(i).getTag()).isEqualTo(tagList.get(i));
  }

  private static EditParams getEditParamsMember(String hashtag) {
    return new EditParams("edited title", "edited content", hashtag, PostTypeEnum.MEMBER, "");
  }
}
