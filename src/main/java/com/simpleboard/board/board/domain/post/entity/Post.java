package com.simpleboard.board.board.domain.post.entity;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.CreateParams;
import com.simpleboard.board.board.domain.post.dto.DeleteParams;
import com.simpleboard.board.board.domain.post.dto.EditParams;
import com.simpleboard.board.board.domain.post.exception.PostDeletePermissionException;
import com.simpleboard.board.board.domain.post.exception.PostPasswordNotMatchException;
import com.simpleboard.board.board.domain.post.vo.BoardVO;
import com.simpleboard.board.board.domain.post.vo.LikesVO;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import com.simpleboard.board.board.domain.post.vo.TagsVO;
import java.time.LocalDateTime;

/**
 * <b>Post</b> Aggregate Root.
 *
 * <p>Post의 생성, 수정, 삭제 담당
 *
 * <p>포함 엔티티
 *
 * <ul>
 *   <li>HashTag
 *   <li>PostLike
 * </ul>
 *
 * @domain aggregate-root
 * @since 1.0
 */


public abstract class Post {

  Long id;
  String title;
  String content;
  Long viewCount;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  Boolean isDeleted;
  BoardVO board;
  TagsVO tags;
  LikesVO likes;

  protected Post(CreateParams params) {
    this.title = params.title();
    this.content = params.content();
    viewCount = 0L;
    isDeleted = false;
    board = BoardVO.of(params.boardId(), params.boardName());
    tags = TagsVO.createTags(params.hashTags());
    likes = LikesVO.create();
  }

  /**
   * Post 생성 static 메서드.
   *
   * <p>GuestPost와 MemberPost중 적절한 객체를 생성
   *
   * @since 1.0
   */
  public static Post write(CreateParams params) {
    if (params.type() == PostTypeEnum.GUEST) return new GuestPost(params);
    if (params.type() == PostTypeEnum.MEMBER) return new MemberPost(params);
    return null;
  }

  /**
   * Post 삭제 메서드.
   *
   * <p>권한 체크 후 soft deletion 적용
   *
   * @throws PostDeletePermissionException MemberPost에서 author가 아닌 유저가 삭제 시도시 발생
   * @throws PostPasswordNotMatchException GuestPost의 password 불일치
   * @since 1.0
   */
  public void deletePost(Visitor visitor, DeleteParams params) {
    checkDeletePermission(visitor, params);
    softDelete();
  }

  /**
   * Post 수정 메서드.
   *
   * <p>권한 체크 후 Post와 HashTag 수정
   *
   * @throws PostDeletePermissionException MemberPost에서 author가 아닌 유저가 삭제 시도시 발생
   * @throws PostPasswordNotMatchException GuestPost의 password 불일치
   * @since 1.0
   */
  public void editPost(Visitor visitor, EditParams params) {
    checkEditPermission(visitor, params);
    edit(params);
  }

  /**
   * 삭제 권한 체크 메서드.
   *
   * <p>권한 미보유시 throw exception
   *
   * @throws PostDeletePermissionException MemberPost에서 author가 아닌 유저가 삭제 시도시 발생
   * @throws PostPasswordNotMatchException GuestPost의 password 불일치
   * @since 1.0
   */
  protected abstract void checkDeletePermission(Visitor visitor, DeleteParams params);

  protected abstract void checkEditPermission(Visitor visitor, EditParams params);

  private void softDelete() {
    isDeleted = true;
  }

  private void edit(EditParams params) {
    this.title = params.title();
    this.content = params.content();
    this.tags = TagsVO.createTags(params.hashTags());
  }
}
