package com.simpleboard.board.board.domain.post.vo;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.LikeInfo;
import com.simpleboard.board.board.domain.post.entity.PostLike;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <b>Likes</b> Value Object.
 *
 * <p>Post에 대한 Likes 관련 책임 담당 VO
 *
 * @domain value-object
 * @since 1.0
 */
public record LikesVO(List<PostLike> likes) {

  /**
   * <b>생성 static 메서드.</b>
   *
   * <p>빈 list 생성 후 반환
   *
   * @since 1.0
   */
  public static LikesVO create() {
    return new LikesVO(new ArrayList<>());
  }

  /**
   * <b>Like 토글 메서드</b>
   *
   * <p>Visitor에 해당하는 PostLike가 있다면 삭제, 없다면 생성
   *
   * @since 1.0
   */
  public LikeInfo toggleLike(Visitor visitor, PostTypeEnum postType) {
    PostLike postLike = getPostLike(visitor, postType);
    if (postLike == null) addLike(visitor);
    else deleteLike(postLike);

    return new LikeInfo(postLike == null, likes.size());
  }

  /**
   * <b>visitor에 대한 PostLike 탐색 메서드.</b>
   *
   * <p>visitor가 생성한 PostLike가 존재하는지 확인한다.
   *
   * @since 1.0
   */
  public Boolean isLiked(Visitor visitor, PostTypeEnum postType) {
    return getPostLike(visitor, postType) != null;
  }

  private PostLike getPostLike(Visitor visitor, PostTypeEnum postType) {
    Optional<PostLike> postLike =
        likes.stream().filter(v -> v.isLiker(visitor, postType)).findFirst();
    return postLike.orElse(null);
  }

  private void addLike(Visitor visitor) {
    likes.add(PostLike.of(visitor));
  }

  private void deleteLike(PostLike postLike) {
    likes.remove(postLike);
  }
}
