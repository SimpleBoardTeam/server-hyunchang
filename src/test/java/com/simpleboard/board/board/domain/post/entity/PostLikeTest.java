package com.simpleboard.board.board.domain.post.entity;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.testutil.VisitorUtil;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostLikeTest {

  @Test
  @DisplayName("PostLike 생성 테스트 - Guest")
  void postLike_Create_Guest_Test() {
    String vid = "vid";
    Visitor visitor = VisitorUtil.guest(vid);

    PostLike postLike = PostLike.of(visitor);

    assertThat(postLike.getVid()).isEqualTo(vid);
    assertThat(postLike.getLikedMemberId()).isNull();
  }

  @Test
  @DisplayName("PostLike 생성 테스트 - Member")
  void postLike_Create_Member_Test() {
    String vid = "vid";
    Long memberId = 1L;
    Visitor visitor = VisitorUtil.member(vid, memberId);

    PostLike postLike = PostLike.of(visitor);

    assertThat(postLike.getVid()).isEqualTo(vid);
    assertThat(postLike.getLikedMemberId()).isEqualTo(memberId);
  }

  @Test
  @DisplayName("isLiker - GuestPost & Guest 테스트")
  void isLiker_GuestPost_And_Guest_Test() {
    String vid = "vid";
    Visitor visitor = VisitorUtil.guest(vid);
    PostLike postLike = PostLike.of(visitor);

    String wrongVid = "wrongVid";
    Visitor wrongVisitor = VisitorUtil.guest(wrongVid);

    assertThat(postLike.isLiker(visitor, PostTypeEnum.GUEST)).isTrue();
    assertThat(postLike.isLiker(wrongVisitor, PostTypeEnum.GUEST)).isFalse();
  }

  @Test
  @DisplayName("isLiker - GuestPost & Member 테스트")
  void isLiker_GuestPost_And_Member_Test() {
    String vid = "vid";
    Visitor visitor = VisitorUtil.guest(vid);

    Long memberId = 1L;
    Visitor sameVidMember = VisitorUtil.member(vid, memberId); // vid가 같은 member

    String wrongVid = "wrongVid";
    Visitor wrongVidMember =
        VisitorUtil.member(wrongVid, memberId); // vid가 다른 member, sameVidMember와 memberId가 같음

    PostLike postLike = PostLike.of(visitor);

    // 처음에는 wrongVidMember 대해 false
    assertThat(postLike.isLiker(wrongVidMember, PostTypeEnum.GUEST)).isFalse();

    // sameVidMember에 대해 검증하며 sameVidMember의 memberId를 저장
    assertThat(postLike.isLiker(sameVidMember, PostTypeEnum.GUEST)).isTrue();
    // sameVidMember와 wrongVidMember는 memberId가 같음
    assertThat(postLike.isLiker(wrongVidMember, PostTypeEnum.GUEST)).isTrue();
  }

  @Test
  @DisplayName("isLiker - MemberPost & Guest 테스트")
  void isLiker_MemberPost_And_Guest_Test() {
    String vid = "vid";
    Long memberId = 1L;
    Visitor visitor = VisitorUtil.member(vid, memberId);

    Visitor sameVidGuest = VisitorUtil.guest(vid);

    String wrongVid = "wrongVid";
    Visitor wrongVidGuest = VisitorUtil.guest(wrongVid);

    PostLike postLike = PostLike.of(visitor);

    assertThat(postLike.isLiker(sameVidGuest, PostTypeEnum.GUEST)).isTrue();
    assertThat(postLike.isLiker(wrongVidGuest, PostTypeEnum.GUEST)).isFalse();
  }

  @Test
  @DisplayName("isLiker - MemberPost & Member 테스트")
  void isLiker_memberPost_And_Member_Test() {
    String vid = "vid";
    Long memberId = 1L;
    Visitor visitor = VisitorUtil.member(vid, memberId);

    Long wrongMemberId = 1002L;
    Visitor sameVidMember = VisitorUtil.member(vid, wrongMemberId); // vid만 같은 멤버

    String wrongVid = "wrongVid";
    Visitor sameMemberIdMember = VisitorUtil.member(wrongVid, memberId); // memberId만 같은 멤버

    Visitor wrongMember = VisitorUtil.member(wrongVid, wrongMemberId); // 둘다 다른 멤버

    PostLike postLike = PostLike.of(visitor);

    assertThat(postLike.isLiker(sameVidMember, PostTypeEnum.MEMBER)).isFalse();
    assertThat(postLike.isLiker(sameMemberIdMember, PostTypeEnum.MEMBER)).isTrue();
    assertThat(postLike.isLiker(wrongMember, PostTypeEnum.MEMBER)).isFalse();
  }
}
