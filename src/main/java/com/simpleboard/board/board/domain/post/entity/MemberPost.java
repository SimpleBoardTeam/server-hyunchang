package com.simpleboard.board.board.domain.post.entity;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import com.simpleboard.board.board.domain.post.dto.PostCreateParams;
import com.simpleboard.board.board.domain.post.dto.PostDeleteParams;
import com.simpleboard.board.board.domain.post.dto.PostEditParams;
import com.simpleboard.board.board.domain.post.exception.MemberPostPermissionException;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * <b>Post 구현체</b> Aggregate Root.
 *
 * <p>Member가 작성한 Post 구현체
 *
 * @since 1.0
 */
@Getter
@SuperBuilder
public class MemberPost extends Post {

  private Long memberId;

  public MemberPost(PostCreateParams spec) {
    super(spec);
    this.memberId = spec.authorId();
  }

  @Override
  protected void checkDeletePermission(Visitor visitor, PostDeleteParams params) {
    checkPermission(visitor);
  }

  @Override
  protected void checkEditPermission(Visitor visitor, PostEditParams params) {
    checkPermission(visitor);
  }

  @Override
  protected PostTypeEnum getPostType() {
    return PostTypeEnum.MEMBER;
  }

  private void checkPermission(Visitor visitor) {
    if (visitor.type() != VisitorType.MEMBER)
      throw new MemberPostPermissionException("Visitor is not member");
    if (!visitor.memberId().equals(memberId))
      throw new MemberPostPermissionException("Wrong member id");
  }
}
