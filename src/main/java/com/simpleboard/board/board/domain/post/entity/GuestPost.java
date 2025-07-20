package com.simpleboard.board.board.domain.post.entity;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.CreateParams;
import com.simpleboard.board.board.domain.post.dto.DeleteParams;
import com.simpleboard.board.board.domain.post.dto.EditParams;
import com.simpleboard.board.board.domain.post.exception.PostPasswordNotMatchException;
import lombok.Getter;

/**
 * <b>Post 구현체</b> Aggregate Root.
 *
 * <p>Guest가 생성한 Post 구현체
 *
 * @since 1.0
 */
@Getter
public class GuestPost extends Post {
  private String nickname;
  private String password;

  public GuestPost(CreateParams spec) {
    super(spec);
    this.nickname = spec.nickname();
    this.password = spec.password();
  }

  @Override
  protected void checkDeletePermission(Visitor visitor, DeleteParams params) {
    if (!this.password.equals(params.password())) throw new PostPasswordNotMatchException(null);
  }

  @Override
  protected void checkEditPermission(Visitor visitor, EditParams params) {
    if (!this.password.equals(params.password())) throw new PostPasswordNotMatchException(null);
  }
}
