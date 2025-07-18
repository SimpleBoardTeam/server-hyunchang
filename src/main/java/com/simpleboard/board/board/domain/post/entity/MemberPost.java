package com.simpleboard.board.board.domain.post.entity;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import com.simpleboard.board.board.domain.post.dto.CreateParams;
import com.simpleboard.board.board.domain.post.dto.DeleteParams;
import com.simpleboard.board.board.domain.post.dto.EditParams;
import com.simpleboard.board.board.domain.post.exception.PostDeletePermissionException;

/**
 * <b>Post 구현체</b> Aggregate Root.
 *
 * <p>Member가 작성한 Post 구현체
 *
 * @since 1.0
 */
public class MemberPost extends Post {

  Long memberId;

  public MemberPost(CreateParams spec) {
    super(spec);
    this.memberId = spec.authorId();
  }

  @Override
  protected void checkDeletePermission(Visitor visitor, DeleteParams params) {
    if (visitor.type() != VisitorType.MEMBER) throw new PostDeletePermissionException(null);
  }

  @Override
  protected void checkEditPermission(Visitor visitor, EditParams params) {
    if (visitor.type() != VisitorType.MEMBER) throw new PostDeletePermissionException(null);
  }
}
