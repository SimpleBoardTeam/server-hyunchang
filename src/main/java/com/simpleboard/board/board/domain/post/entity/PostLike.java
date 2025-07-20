package com.simpleboard.board.board.domain.post.entity;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import lombok.Getter;

/**
 * <b>PostLike</b> Entity(non‑root)
 *
 * <p>Post에 대한 like 정보
 *
 * @domain entity
 * @since 1.0
 */
@Getter
public class PostLike {
  private String vid;
  private Long likedMemberId;

  private PostLike(String vid, Long memberId) {
    this.vid = vid;
    this.likedMemberId = memberId;
  }

  public static PostLike of(Visitor visitor) {
    return new PostLike(visitor.vid(), visitor.memberId());
  }

  /**
   * <b>좋아요 확인 메서드</b>
   *
   * <p>visitor가 해당 PostLike에 대한 생성자인지 체크
   *
   * <p>Visitor가 Member이고, Member의 vid가 PostLike의 vid와 같을 경우, PostLike의 memberId에 visitor의 memberId
   * 추가
   *
   * @since 1.0
   */
  public boolean isLiker(Visitor visitor, PostTypeEnum postType) {

    // 1. Post가 Member라면 MemberId값만 본다.
    if (postType == PostTypeEnum.MEMBER) return checkMemberPost(visitor);

    // 2. GuestPost지만 PostLike에 MemberId가 기록되어있을 때
    if (likedMemberId != null) {

      // 2-1. 방문자가 member일 때
      if (visitor.type() == VisitorType.MEMBER) {
        // 2-2. MemberId가 같으면 true
        if (likedMemberId.equals(visitor.memberId())) return true;
      }
      return vid.equals(visitor.vid());
      // 2-3. MemberId가 달라도 vid가 같으면 true, vid까지 다르면 false
    }

    // 3. GuestPost이고, PostLike에 MemberId가 없을 때
    if (vid.equals(visitor.vid())) {

      likedMemberId = visitor.memberId();
      return true;
    }

    return false;
  }

  // MemberPost에서는 무조건 멤버아이디만 체크
  private boolean checkMemberPost(Visitor visitor) {
    if (visitor.type() != VisitorType.MEMBER) return false;
    if (likedMemberId.equals(visitor.memberId())) {
      vid = visitor.vid();
      return true;
    }

    return false;
  }
}
