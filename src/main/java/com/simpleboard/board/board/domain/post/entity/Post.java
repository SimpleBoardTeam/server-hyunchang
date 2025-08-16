package com.simpleboard.board.board.domain.post.entity;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.LikeInfo;
import com.simpleboard.board.board.domain.post.dto.PostCreateParams;
import com.simpleboard.board.board.domain.post.dto.PostDeleteParams;
import com.simpleboard.board.board.domain.post.dto.PostEditParams;
import com.simpleboard.board.board.domain.post.exception.MemberPostPermissionException;
import com.simpleboard.board.board.domain.post.exception.PostPasswordNotMatchException;
import com.simpleboard.board.board.domain.post.vo.BoardVO;
import com.simpleboard.board.board.domain.post.vo.LikesVO;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import com.simpleboard.board.board.domain.post.vo.TagsVO;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

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
@Getter
@SuperBuilder
public abstract class Post {

  private Long id;
  private String title;
  private String content;
  private Long viewCount;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean isDeleted;
  private BoardVO board;
  private TagsVO tags;
  private LikesVO likes;

  protected Post(PostCreateParams params) {
    this.title = params.title();
    this.content = params.content();
    viewCount = 0L;
    isDeleted = false;
    board = BoardVO.of(params.boardId());
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
  public static Post write(PostCreateParams params) {
    if (params.type() == PostTypeEnum.GUEST) return new GuestPost(params);
    if (params.type() == PostTypeEnum.MEMBER) return new MemberPost(params);
    return null;
  }

  /**
   * Post 삭제 메서드.
   *
   * <p>권한 체크 후 soft deletion 적용
   *
   * @throws MemberPostPermissionException MemberPost에서 author가 아닌 유저가 삭제 시도시 발생
   * @throws PostPasswordNotMatchException GuestPost의 password 불일치
   * @since 1.0
   */
  public void deletePost(Visitor visitor, PostDeleteParams params) {
    checkDeletePermission(visitor, params);
    softDelete();
  }

  /**
   * Post 수정 메서드.
   *
   * <p>권한 체크 후 Post와 HashTag 수정
   *
   * @throws MemberPostPermissionException MemberPost에서 author가 아닌 유저가 삭제 시도시 발생
   * @throws PostPasswordNotMatchException GuestPost의 password 불일치
   * @since 1.0
   */
  public void editPost(Visitor visitor, PostEditParams params) {
    checkEditPermission(visitor, params);
    edit(params);
  }

  /**
   * <b>PostLike를 toggle하는 메서드</b>
   *
   * <p>LikesVO를 통해 Visitor의 PostLike 존재 유무를 체크,
   *
   * <p>존재시 삭제, 미존재시 생성
   *
   * @return 토글 후 Like 여부, Post의 총 PostLike 수
   * @since 1.0
   */
  public LikeInfo toggleLike(Visitor visitor) {
    return likes.toggleLike(visitor, getPostType());
  }

  /**
   * 수정/삭제 권한 체크 메서드.
   *
   * <p>권한 미보유시 throw exception
   *
   * @throws MemberPostPermissionException MemberPost에서 author가 아닌 유저가 삭제 시도시 발생
   * @throws PostPasswordNotMatchException GuestPost의 password 불일치
   * @since 1.0
   */
  protected abstract void checkEditPermission(Visitor visitor, PostEditParams params);

  protected abstract void checkDeletePermission(Visitor visitor, PostDeleteParams params);

  /**
   * <b>Post의 타입 확인 메서드</b>
   *
   * <p>Post의 구현체가 해당 메서드를 구현하여 구현체에 맞는 Post 타입 반환
   *
   * @since 1.0
   */
  protected abstract PostTypeEnum getPostType();

  private void softDelete() {
    isDeleted = true;
  }

  private void edit(PostEditParams params) {
    this.title = params.title();
    this.content = params.content();
    this.tags = TagsVO.createTags(params.hashTags());
  }
}
