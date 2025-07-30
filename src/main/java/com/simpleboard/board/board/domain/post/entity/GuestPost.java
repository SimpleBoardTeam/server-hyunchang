package com.simpleboard.board.board.domain.post.entity;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.PostCreateParams;
import com.simpleboard.board.board.domain.post.dto.PostDeleteParams;
import com.simpleboard.board.board.domain.post.dto.PostEditParams;
import com.simpleboard.board.board.domain.post.exception.PostPasswordNotMatchException;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * <b>Post 구현체</b> Aggregate Root.
 *
 * <p>Guest가 생성한 Post 구현체
 *
 * @since 1.0
 */
@Getter
@SuperBuilder
public class GuestPost extends Post {
  private String nickname;
  private String password;

  public GuestPost(PostCreateParams spec) {
    super(spec);
    this.nickname = spec.nickname();
    this.password = spec.password();
  }

  @Override
  protected void checkDeletePermission(Visitor visitor, PostDeleteParams params) {
    if (!this.password.equals(params.password())) throw new PostPasswordNotMatchException(null);
  }

  @Override
  protected void checkEditPermission(Visitor visitor, PostEditParams params) {
    if (!this.password.equals(params.password())) throw new PostPasswordNotMatchException(null);
  }

  @Override
  protected PostTypeEnum getPostType() {
    return PostTypeEnum.GUEST;
  }
}
