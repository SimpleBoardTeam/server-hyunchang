package com.simpleboard.board.board.domain.post.entity;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.CreateParams;
import com.simpleboard.board.board.domain.post.dto.LikeInfo;
import com.simpleboard.board.board.domain.post.testutil.PostCreateParamsBuilder;
import com.simpleboard.board.board.domain.post.testutil.VisitorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <b>Post에 대한 Likes 토클 테스트 클래스</b>
 *
 * <p>GuestPost/MemberPost 양쪽에서 Likes 토클이 정상 작동하는지 테스트
 */
public class PostPostLikeToggleTest {

  private Long memberId = 1001L;

  @Test
  @DisplayName("GuestPost toggle 테스트")
  void guestPost_Toggle_Test() {
    Visitor visitor1 = VisitorUtil.guest("vid1");
    Visitor visitor2 = VisitorUtil.guest("vid2");
    Visitor visitor1_dup = VisitorUtil.guest("vid1");

    CreateParams params = PostCreateParamsBuilder.builder(visitor1).build();
    Post post = Post.write(params);

    post.toggleLike(visitor1);
    LikeInfo likeInfo1 = post.toggleLike(visitor2);

    assertThat(likeInfo1.isLiked()).isTrue();
    assertThat(likeInfo1.likeCount()).isEqualTo(2);

    LikeInfo likeInfo2 = post.toggleLike(visitor1_dup);
    assertThat(likeInfo2.isLiked()).isFalse();
    assertThat(likeInfo2.likeCount()).isEqualTo(1);
  }

  @Test
  @DisplayName("MemberPost toggle 테스트")
  void memberPost_Toggle_Test() {
    Visitor visitor1 = VisitorUtil.member("vid1", memberId);
    Visitor visitor2 = VisitorUtil.guest("vid2");
    Visitor visitor1_dup = VisitorUtil.member("vid3", memberId);

    CreateParams params = PostCreateParamsBuilder.builder(visitor1).build();
    Post post = Post.write(params);

    post.toggleLike(visitor1);
    LikeInfo likeInfo1 = post.toggleLike(visitor2);

    assertThat(likeInfo1.isLiked()).isTrue();
    assertThat(likeInfo1.likeCount()).isEqualTo(2);

    LikeInfo likeInfo2 = post.toggleLike(visitor1_dup);
    assertThat(likeInfo2.isLiked()).isFalse();
    assertThat(likeInfo2.likeCount()).isEqualTo(1);
  }
}
