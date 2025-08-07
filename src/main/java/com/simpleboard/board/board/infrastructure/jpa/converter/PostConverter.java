package com.simpleboard.board.board.infrastructure.jpa.converter;

import com.simpleboard.board.board.domain.post.entity.*;
import com.simpleboard.board.board.domain.post.vo.BoardVO;
import com.simpleboard.board.board.domain.post.vo.LikesVO;
import com.simpleboard.board.board.domain.post.vo.TagsVO;
import com.simpleboard.board.board.infrastructure.jpa.entity.*;
import com.simpleboard.board.board.infrastructure.jpa.entity.embed.BoardEmbed;
import com.simpleboard.board.board.infrastructure.jpa.entity.embed.LikesEmbed;
import com.simpleboard.board.board.infrastructure.jpa.entity.embed.TagsEmbed;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * <b>Post: Domain Entity <-> JPA Entity Converter 클래스</b>
 *
 * <p>Post aggregate의 도메인 entity, VO와 JPA entity를 상호 변환한다.
 */
@Component
public class PostConverter {

  /**
   * <b>Domain Post 엔티티를 JPA Post 엔티티로 변환하는 메서드</b>
   *
   * <p>내부의 서브 엔티티 및 VO까지 전부 변환한다.
   *
   * <p>단, HashTag와 TagVO의 경우 별도의 작업이 필요(DB조회 필요)
   *
   * @param post: Post Domain entity
   * @return Post JPA Entity
   */
  public PostEntity toJpaEntity(Post post) {
    if (post instanceof GuestPost) {
      GuestPost guestPost = (GuestPost) post;
      return GuestPostEntity.builder()
          .id(post.getId())
          .title(post.getTitle())
          .content(post.getContent())
          .viewCount(post.getViewCount())
          .board(toJpaEntity(post.getBoard()))
          .isDeleted(post.getIsDeleted())
          .tags(TagsEmbed.builder().tags(new ArrayList<>()).build())
          .likes(toJpaEntity(post.getLikes()))
          .nickname(guestPost.getNickname())
          .password(guestPost.getPassword())
          .build();

    } else if (post instanceof MemberPost) {
      MemberPost memberPost = (MemberPost) post;
      return MemberPostEntity.builder()
          .id(post.getId())
          .title(post.getTitle())
          .content(post.getContent())
          .viewCount(post.getViewCount())
          .board(toJpaEntity(post.getBoard()))
          .isDeleted(post.getIsDeleted())
          .tags(TagsEmbed.builder().tags(new ArrayList<>()).build())
          .likes(toJpaEntity(post.getLikes()))
          .memberId(memberPost.getMemberId())
          .build();
    }
    return null;
  }

  /**
   * <b>JPA Post 엔티티를 Domain Post 엔티티로 변환해주는 메서드</b>
   *
   * <p>내부의 서브 엔티티 및 embed 클래스까지 모두 변환한다.
   *
   * @param post: Post JPA entity
   * @return Post domain entity
   */
  public Post toDomainEntity(PostEntity post) {
    if (post instanceof GuestPostEntity) {
      GuestPostEntity guestPost = (GuestPostEntity) post;
      return GuestPost.builder()
          .id(post.getId())
          .title(post.getTitle())
          .content(post.getContent())
          .viewCount(post.getViewCount())
          .board(toDomainEntity(post.getBoard()))
          .isDeleted(post.getIsDeleted())
          .tags(toDomainEntity(post.getTags()))
          .likes(toDomainEntity(post.getLikes()))
          .nickname(guestPost.getNickname())
          .password(guestPost.getPassword())
          .build();
    } else if (post instanceof MemberPostEntity) {
      MemberPostEntity memberPost = (MemberPostEntity) post;
      return MemberPost.builder()
          .id(post.getId())
          .title(post.getTitle())
          .content(post.getContent())
          .viewCount(post.getViewCount())
          .board(toDomainEntity(post.getBoard()))
          .isDeleted(post.getIsDeleted())
          .tags(toDomainEntity(post.getTags()))
          .likes(toDomainEntity(post.getLikes()))
          .memberId(memberPost.getMemberId())
          .build();
    }
    return null;
  }

  private LikesEmbed toJpaEntity(LikesVO vo) {
    return LikesEmbed.builder().likes(vo.likes().stream().map(this::toJpaEntity).toList()).build();
  }

  private PostLikeEntity toJpaEntity(PostLike postLike) {
    return PostLikeEntity.builder()
        .id(postLike.getId())
        .vid(postLike.getVid())
        .likedMemberId(postLike.getLikedMemberId())
        .post(null)
        .build();
  }

  private BoardEmbed toJpaEntity(BoardVO board) {
    return BoardEmbed.builder().boardId(board.boardId()).boardName(board.boardName()).build();
  }

  private BoardVO toDomainEntity(BoardEmbed embed) {
    return BoardVO.of(embed.boardId(), embed.boardName());
  }

  private TagsVO toDomainEntity(TagsEmbed embed) {
    return TagsVO.builder()
        .tags(embed.getTags().stream().map(this::toDomainEntity).toList())
        .build();
  }

  private LikesVO toDomainEntity(LikesEmbed embed) {
    var likes =
        embed.getLikes().stream()
            .map(this::toDomainEntity)
            .collect(Collectors.toCollection(ArrayList::new));

    return LikesVO.builder().likes(likes).build();
  }

  private HashTag toDomainEntity(HashTagEntity entity) {
    return HashTag.builder().id(entity.getId()).tag(entity.getTag()).build();
  }

  private PostLike toDomainEntity(PostLikeEntity entity) {
    return PostLike.builder()
        .id(entity.getId())
        .vid(entity.getVid())
        .likedMemberId(entity.getLikedMemberId())
        .build();
  }
}
