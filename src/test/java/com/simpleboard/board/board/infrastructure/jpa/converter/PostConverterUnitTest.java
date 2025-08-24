package com.simpleboard.board.board.infrastructure.jpa.converter;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.PostCreateParams;
import com.simpleboard.board.board.domain.post.entity.GuestPost;
import com.simpleboard.board.board.domain.post.entity.MemberPost;
import com.simpleboard.board.board.domain.post.entity.Post;
import com.simpleboard.board.board.domain.testUtil.PostCreateParamsBuilder;
import com.simpleboard.board.board.domain.testUtil.VisitorUtil;
import com.simpleboard.board.board.infrastructure.jpa.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostConverterUnitTest {

  private String vid1 = "vid1";
  private String vid2 = "vid2";
  private Long memberId1 = 1001L;
  private Long memberId2 = 1002L;

  private PostConverter postConverter = new PostConverter();

  @Test
  @DisplayName("Convert Domain to JPA entity GuestPost success test")
  void Domain_To_Jpa_Convert_GuestPost_Test() {
    Post domainPost = getGuestPost();

    PostEntity jpaPost = postConverter.toJpaEntity(domainPost);

    checkCommonFields(jpaPost, domainPost);

    checkGuestFields(jpaPost, domainPost);
  }

  @Test
  @DisplayName("Convert Domain to JPA entity MemberPost success test")
  void Domain_To_Jpa_Convert_MemberPost_Test() {
    Post domainPost = getMemberPost();

    PostEntity jpaPost = postConverter.toJpaEntity(domainPost);

    checkCommonFields(jpaPost, domainPost);

    checkMemberFields(jpaPost, domainPost);
  }

  @Test
  @DisplayName("Convert JPA to Domain entity GuestPost success test")
  void Jpa_To_Domain_Convert_GuestPost_Test() {
    Post domainPost = getGuestPost();
    PostEntity jpaPost = postConverter.toJpaEntity(domainPost);

    Post domainEntity = postConverter.toDomainEntity(jpaPost);

    checkCommonFields(jpaPost, domainEntity);
    checkGuestFields(jpaPost, domainEntity);
  }

  @Test
  @DisplayName("Convert JPA to Domain entity GuestPost success test")
  void Jpa_To_Domain_Convert_MemberPost_Test() {
    Post domainPost = getMemberPost();
    PostEntity jpaPost = postConverter.toJpaEntity(domainPost);

    Post domainEntity = postConverter.toDomainEntity(jpaPost);

    checkCommonFields(jpaPost, domainEntity);
    checkMemberFields(jpaPost, domainEntity);
  }

  private Post getGuestPost() {
    Visitor visitor1 = VisitorUtil.guest(vid1);
    Visitor visitor2 = VisitorUtil.guest(vid2);
    PostCreateParams params = PostCreateParamsBuilder.builder(visitor1).build();
    Post domainPost = Post.write(params);
    domainPost.toggleLike(visitor1);
    domainPost.toggleLike(visitor2);
    return domainPost;
  }

  private Post getMemberPost() {
    Visitor visitor1 = VisitorUtil.member(vid1, memberId1);
    Visitor visitor2 = VisitorUtil.member(vid2, memberId2);
    PostCreateParams params = PostCreateParamsBuilder.builder(visitor1).build();
    Post domainPost = Post.write(params);
    domainPost.toggleLike(visitor1);
    domainPost.toggleLike(visitor2);
    return domainPost;
  }

  private void checkGuestFields(PostEntity jpaPost, Post domainPost) {
    GuestPostEntity jpaGuestPost = (GuestPostEntity) jpaPost;
    GuestPost domainGuestPost = (GuestPost) domainPost;

    assertThat(jpaGuestPost.getNickname()).isEqualTo(domainGuestPost.getNickname());
    assertThat(jpaGuestPost.getPassword()).isEqualTo(domainGuestPost.getPassword());
  }

  private void checkMemberFields(PostEntity jpaPost, Post domainPost) {
    MemberPostEntity jpaMemberPost = (MemberPostEntity) jpaPost;
    MemberPost domainMemberPost = (MemberPost) domainPost;

    assertThat(jpaMemberPost.getMemberId()).isEqualTo(domainMemberPost.getMemberId());
  }

  private void checkCommonFields(PostEntity jpaPost, Post domainPost) {
    assertThat(jpaPost.getBoard().boardId()).isEqualTo(domainPost.getBoard().boardId());
    assertThat(jpaPost.getTitle()).isEqualTo(domainPost.getTitle());
    assertThat(jpaPost.getContent()).isEqualTo(domainPost.getContent());
    assertThat(jpaPost.getViewCount()).isEqualTo(domainPost.getViewCount());
    assertThat(jpaPost.getIsDeleted()).isEqualTo(domainPost.getIsDeleted());
    assertThat(jpaPost.getTags().getTags())
        .extracting(HashTagEntity::getTag)
        .containsExactlyElementsOf(
            domainPost.getTags().tags().stream().map(t -> t.getTag()).toList());
    assertThat(jpaPost.getLikes().getLikes())
        .extracting(PostLikeEntity::getVid)
        .containsExactlyElementsOf(
            domainPost.getLikes().likes().stream().map(l -> l.getVid()).toList());
  }
}
