package com.simpleboard.board.board.domain.post.vo;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.LikeInfo;
import com.simpleboard.board.board.domain.post.entity.PostLike;
import com.simpleboard.board.board.domain.post.testutil.VisitorUtil;
import org.junit.jupiter.api.*;

/**
 * <b>LikesVO 테스트</b>
 *
 * <p>toggleLike / isLiked / side-effect 케이스 전반 검증
 */
class LikesVOTest {

  @Test
  @DisplayName("create() 초기 상태")
  void create_initial_state() {
    LikesVO vo = LikesVO.create();
    assertThat(vo.likes()).isEmpty();
  }

  @Test
  @DisplayName("GuestPost - Guest 토글(add/remove)")
  void toggle_guestPost_guest() {
    LikesVO vo = LikesVO.create();
    Visitor guest = VisitorUtil.guest("vid-1");

    LikeInfo addInfo = vo.toggleLike(guest, PostTypeEnum.GUEST);
    assertThat(addInfo.isLiked()).isTrue();
    assertThat(addInfo.likeCount()).isEqualTo(1);
    assertThat(vo.isLiked(guest, PostTypeEnum.GUEST)).isTrue();

    LikeInfo removeInfo = vo.toggleLike(guest, PostTypeEnum.GUEST);
    assertThat(removeInfo.isLiked()).isFalse();
    assertThat(removeInfo.likeCount()).isEqualTo(0);
    assertThat(vo.isLiked(guest, PostTypeEnum.GUEST)).isFalse();
  }

  @Test
  @DisplayName("MemberPost - Member 토글(add/remove)")
  void toggle_memberPost_member() {
    LikesVO vo = LikesVO.create();
    Visitor member = VisitorUtil.member("mvid-1", 10L);

    LikeInfo add = vo.toggleLike(member, PostTypeEnum.MEMBER);
    assertThat(add.isLiked()).isTrue();
    assertThat(add.likeCount()).isEqualTo(1);
    assertThat(vo.isLiked(member, PostTypeEnum.MEMBER)).isTrue();

    LikeInfo remove = vo.toggleLike(member, PostTypeEnum.MEMBER);
    assertThat(remove.isLiked()).isFalse();
    assertThat(remove.likeCount()).isEqualTo(0);
    assertThat(vo.isLiked(member, PostTypeEnum.MEMBER)).isFalse();
  }

  @Test
  @DisplayName("GuestPost - Guest like 후 동일 vid Member 접근 시 likedMemberId 세팅")
  void guestLike_then_memberSameVid_setsMemberId() {
    LikesVO vo = LikesVO.create();
    String vid = "share-vid";
    Visitor guest = VisitorUtil.guest(vid);
    Visitor memberSameVid = VisitorUtil.member(vid, 99L);

    vo.toggleLike(guest, PostTypeEnum.GUEST); // 추가
    PostLike stored = vo.likes().get(0);
    assertThat(stored.getLikedMemberId()).isNull();

    // 동일 vid Member 가 접근 → isLiker 로직이 likedMemberId 채움
    boolean likedByMember = vo.isLiked(memberSameVid, PostTypeEnum.GUEST);
    assertThat(likedByMember).isTrue();
    assertThat(stored.getLikedMemberId()).isEqualTo(99L);

    // 같은 memberId, 다른 vid: memberId 동일 → 2-2 분기 true
    Visitor sameMemberDifferentVid = VisitorUtil.member("another-vid", 99L);
    assertThat(vo.isLiked(sameMemberDifferentVid, PostTypeEnum.GUEST)).isTrue();
  }

  @Test
  @DisplayName("GuestPost - likedMemberId 세팅 후 분기 2-2 / 2-3 / 불일치 케이스")
  void guestPost_branch_2_2_and_2_3() {
    LikesVO vo = LikesVO.create();
    String vid = "vX";
    Visitor guest = VisitorUtil.guest(vid);
    vo.toggleLike(guest, PostTypeEnum.GUEST);

    // Member 동일 vid → likedMemberId 세팅
    Visitor memberSameVid = VisitorUtil.member(vid, 11L);
    assertThat(vo.isLiked(memberSameVid, PostTypeEnum.GUEST)).isTrue();

    // (2-2) memberId 같음 → vid 달라도 true
    Visitor memberSameIdDifferentVid = VisitorUtil.member("otherVid", 11L);
    assertThat(vo.isLiked(memberSameIdDifferentVid, PostTypeEnum.GUEST)).isTrue();

    // (2-3) memberId 다르고 vid 같음 → vid equals → true
    Visitor memberDifferentIdSameVid = VisitorUtil.member(vid, 999L);
    assertThat(vo.isLiked(memberDifferentIdSameVid, PostTypeEnum.GUEST)).isTrue();

    // 모두 다르면 false
    Visitor memberDifferentBoth = VisitorUtil.member("zzz", 999L);
    assertThat(vo.isLiked(memberDifferentBoth, PostTypeEnum.GUEST)).isFalse();
  }

  @Test
  @DisplayName("MemberPost - 동일 memberId 다른 vid 접근 시 vid 갱신")
  void memberPost_vid_update_side_effect() {
    LikesVO vo = LikesVO.create();
    Visitor member = VisitorUtil.member("vid-A", 100L);
    vo.toggleLike(member, PostTypeEnum.MEMBER);

    PostLike like = vo.likes().get(0);
    assertThat(like.getVid()).isEqualTo("vid-A");

    // 동일 memberId, 다른 vid → checkMemberPost 에서 vid 재설정
    Visitor sameMemberDifferentVid = VisitorUtil.member("vid-B", 100L);
    assertThat(vo.isLiked(sameMemberDifferentVid, PostTypeEnum.MEMBER)).isTrue();
    assertThat(like.getVid()).isEqualTo("vid-B");
  }

  @Test
  @DisplayName("여러 사용자 혼합 후 일부 해제")
  void multiple_users_like_and_unlike() {
    LikesVO vo = LikesVO.create();

    Visitor m1 = VisitorUtil.member("m1", 1L);
    Visitor m2 = VisitorUtil.member("m2", 2L);
    Visitor g1 = VisitorUtil.guest("g1");

    vo.toggleLike(m1, PostTypeEnum.MEMBER); // 1
    vo.toggleLike(m2, PostTypeEnum.MEMBER); // 2
    vo.toggleLike(g1, PostTypeEnum.GUEST); // 3

    assertThat(vo.likes()).hasSize(3);

    LikeInfo removeM2 = vo.toggleLike(m2, PostTypeEnum.MEMBER);
    assertThat(removeM2.isLiked()).isFalse();
    assertThat(removeM2.likeCount()).isEqualTo(2);
    assertThat(vo.likes()).hasSize(2);
  }

  @Test
  @DisplayName("토글 연속 수행 시 LikeInfo.isLiked 플래그 확인")
  void likeInfo_flag_semantics() {
    LikesVO vo = LikesVO.create();
    Visitor member = VisitorUtil.member("mm", 77L);

    LikeInfo first = vo.toggleLike(member, PostTypeEnum.MEMBER);  // add
    LikeInfo second = vo.toggleLike(member, PostTypeEnum.MEMBER); // remove
    LikeInfo third = vo.toggleLike(member, PostTypeEnum.MEMBER);  // add

    assertThat(first.isLiked()).isTrue();
    assertThat(first.likeCount()).isEqualTo(1);

    assertThat(second.isLiked()).isFalse();
    assertThat(second.likeCount()).isEqualTo(0);

    assertThat(third.isLiked()).isTrue();
    assertThat(third.likeCount()).isEqualTo(1);
  }

  @Test
  @DisplayName("Guest + 동일 vid Member 모두 isLiked true")
  void guest_and_member_same_vid_behavior() {
    LikesVO vo = LikesVO.create();
    String vid = "shared";
    Visitor guest = VisitorUtil.guest(vid);
    Visitor member = VisitorUtil.member(vid, 5L);

    vo.toggleLike(guest, PostTypeEnum.GUEST);

    assertThat(vo.isLiked(guest, PostTypeEnum.GUEST)).isTrue();
    assertThat(vo.isLiked(member, PostTypeEnum.GUEST)).isTrue(); // likedMemberId 세팅
  }
}
