package com.simpleboard.board.board.infrastructure.mybatis.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.simpleboard.board.board.application.query.*;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@ActiveProfiles("test")
@Import(PostQueryRepositoryImpl.class)
class PostQueryRepositoryImplMybatisTest {

  @Autowired PostQueryRepository repository;

  @Nested
  @DisplayName("상세 조회")
  class Details {
    @Test
    @DisplayName("회원 방문자 likedMemberId 매칭 → isLiked=true, likeCount/태그 포함")
    void memberLiked() {
      PostDetailsReadModel r =
          repository.getPostDetails(detailsCriteria(VisitorType.MEMBER, "x", 101L, 1L));
      assertThat(r.postId()).isEqualTo(1L);
      assertThat(r.postType()).isEqualTo("MEMBER");
      assertThat(r.isLiked()).isTrue();
      assertThat(r.likeCount()).isEqualTo(2);
      assertThat(r.hashTags()).containsExactlyInAnyOrder("java", "spring");
      assertThat(r.viewCount()).isEqualTo(5L);
    }

    @Test
    @DisplayName("게스트 방문자 vid 매칭 → isLiked=true")
    void guestLiked() {
      PostDetailsReadModel r =
          repository.getPostDetails(detailsCriteria(VisitorType.GUEST, "v-3", null, 2L));
      assertThat(r.postId()).isEqualTo(2L);
      assertThat(r.postType()).isEqualTo("GUEST");
      assertThat(r.isLiked()).isTrue();
      assertThat(r.likeCount()).isEqualTo(1);
      assertThat(r.hashTags()).containsExactly("mybatis");
    }

    @Test
    @DisplayName("관리자 등 기타 → isLiked=false")
    void managerNotLiked() {
      PostDetailsReadModel r =
          repository.getPostDetails(detailsCriteria(VisitorType.MANAGER, "any", 9999L, 1L));
      assertThat(r.isLiked()).isFalse();
    }
  }

  @Nested
  @DisplayName("목록/검색/페이징")
  class ListSearchPaging {

    @Test
    @DisplayName("기본 목록(최신순): size=10, 첫 페이지")
    void list_default_firstPage() {
      PostListReadModel model = repository.getPostList(listCriteria(10L, 0, 10, null, null));
      assertThat(model.posts()).hasSize(10);
      // 최신순: 삭제 제외하면 40,39,38,37,36,35,33,32,31,30 ...
      assertThat(extractIds(model))
          .containsExactly(40L, 39L, 38L, 37L, 36L, 35L, 33L, 32L, 31L, 30L);
      assertThat(model.totalPosts()).isEqualTo(35); // 40개 중 삭제 5개 제외
      assertThat(model.totalComments()).isEqualTo(8); // data.sql에서 ACTIVE 총합
    }

    @Test
    @DisplayName("페이징: page=1,size=10 → 다음 10개")
    void list_paging_secondPage() {
      PostListReadModel model = repository.getPostList(listCriteria(10L, 1, 10, null, null));
      assertThat(extractIds(model))
          .containsExactly(29L, 28L, 27L, 26L, 25L, 24L, 23L, 22L, 20L, 19L);
    }

    @Test
    @DisplayName("페이징: page=2,size=10 → 다음 10개")
    void list_paging_thirdPage() {
      PostListReadModel model = repository.getPostList(listCriteria(10L, 2, 10, null, null));
      assertThat(extractIds(model)).containsExactly(18L, 17L, 16L, 15L, 14L, 12L, 11L, 10L, 9L, 7L);
    }

    @Test
    @DisplayName("페이징: page=3,size=10 → 잔여 5개")
    void list_paging_lastPage() {
      PostListReadModel model = repository.getPostList(listCriteria(10L, 3, 10, null, null));
      assertThat(extractIds(model)).containsExactly(6L, 5L, 4L, 2L, 1L);
    }

    @Test
    @DisplayName("페이징: 다음 페이징 인덱스 반환")
    void paging_test() {
      PostListReadModel model1 = repository.getPostList(listCriteria(10L, 2, 10, null, null));
      PostListReadModel model2 =
          repository.getPostList(listCriteria(10L, model1.page(), model1.size(), null, null));
      assertThat(extractIds(model2)).containsExactly(6L, 5L, 4L, 2L, 1L);
    }

    @Test
    @DisplayName("TITLE 검색: 'Spring' 포함")
    void search_title() {
      PostListReadModel model = repository.getPostList(listCriteria(10L, 0, 10, "TITLE", "Spring"));
      // Spring 포함 글: 36,28,20,12,4,1 (최신순)
      assertThat(extractIds(model)).containsExactly(36L, 28L, 20L, 12L, 4L, 1L);
    }

    @Test
    @DisplayName("NICKNAME 검색: '게스트'")
    void search_nickname() {
      PostListReadModel model = repository.getPostList(listCriteria(10L, 0, 20, "NICKNAME", "게스트"));
      // 게스트 닉네임인 글: 30,26,22,18,14,10,6,2 (34는 삭제)
      assertThat(extractIds(model)).containsExactly(30L, 26L, 22L, 18L, 14L, 10L, 6L, 2L);
      assertThat(model.posts()).allSatisfy(p -> assertThat(p.nickname()).isEqualTo("게스트"));
    }

    @Test
    @DisplayName("CONTENT 검색: 'my' 포함")
    void search_content() {
      PostListReadModel model = repository.getPostList(listCriteria(10L, 0, 10, "CONTENT", "my"));
      // 'my' 포함: 33,24,16,5,2  (최신순)
      assertThat(extractIds(model)).containsExactly(33L, 24L, 16L, 5L, 2L);
    }

    @Test
    @DisplayName("HASHTAG 검색: java")
    void search_hashtag_java() {
      PostListReadModel model = repository.getPostList(listCriteria(10L, 0, 10, "HASHTAG", "java"));
      assertThat(extractIds(model)).containsExactly(36L, 28L, 20L, 12L, 4L, 1L);
    }

    @Test
    @DisplayName("HASHTAG 검색: spring")
    void search_hashtag_spring() {
      PostListReadModel model =
          repository.getPostList(listCriteria(10L, 0, 10, "HASHTAG", "spring"));
      assertThat(extractIds(model)).containsExactly(12L, 1L);
    }

    private List<Long> extractIds(PostListReadModel model) {
      return model.posts().stream().map(PostListReadModel.PostSummary::postId).toList();
    }
  }

  private PostDetailsCriteria detailsCriteria(VisitorType t, String v, Long m, Long p) {
    return PostDetailsCriteria.builder().type(t).vId(v).memberId(m).postId(p).build();
  }

  private PostListCriteria listCriteria(
      Long boardId, int page, int size, String searchType, String keyword) {
    return PostListCriteria.builder()
        .boardId(boardId)
        .page(page)
        .size(size)
        .searchType(searchType)
        .keyword(keyword)
        .build();
  }
}
