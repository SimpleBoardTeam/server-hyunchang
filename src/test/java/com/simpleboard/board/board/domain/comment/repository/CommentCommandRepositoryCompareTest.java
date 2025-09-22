package com.simpleboard.board.board.domain.comment.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.entity.Comment;
import com.simpleboard.board.board.domain.comment.vo.CommentType;
import com.simpleboard.board.board.infrastructure.jpa.converter.CommentConverter;
import com.simpleboard.board.board.infrastructure.jpa.entity.CommentEntity;
import com.simpleboard.board.board.infrastructure.jpa.entity.GuestPostEntity;
import com.simpleboard.board.board.infrastructure.jpa.entity.PostEntity;
import com.simpleboard.board.board.infrastructure.jpa.entity.embed.BoardEmbed;
import com.simpleboard.board.board.infrastructure.jpa.repository.*;
import com.simpleboard.board.testconfig.JpaAuditTestConfig;
import java.util.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * <b>Repository 테스트</b>
 *
 * <p>MySQL DB를 활용한 Command repository v1, v3 비교 테스트
 */
@DataJpaTest
@Transactional(propagation = Propagation.NEVER)
@Testcontainers
@ActiveProfiles("test-container")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
  CommentCommandRepositoryImpl.class,
  CommentCommandRepositoryImpl3.class,
  CommentConverter.class,
  JpaAuditTestConfig.class
})
class CommentCommandRepositoryCompareTest {
  @Container
  static final MySQLContainer<?> MYSQL =
      new MySQLContainer<>("mysql:8.0.36")
          .withDatabaseName("testdb")
          .withUsername("parentTest")
          .withPassword("parentTest");

  @DynamicPropertySource
  static void registerDatasource(DynamicPropertyRegistry r) {
    r.add("spring.datasource.url", MYSQL::getJdbcUrl);
    r.add("spring.datasource.username", MYSQL::getUsername);
    r.add("spring.datasource.password", MYSQL::getPassword);
    r.add("spring.datasource.driver-class-name", MYSQL::getDriverClassName);
    r.add("spring.sql.init.platform", () -> "mysql");
    r.add("spring.sql.init.mode", () -> "always");
  }

  private static final Logger log =
      LoggerFactory.getLogger(CommentCommandRepositoryCompareTest.class);
  private static final int THREADS = 10;

  @Autowired CommentEntityRepository commentEntityRepository;
  @Autowired PostEntityRepository postEntityRepository;
  @Autowired CommentConverter commentConverter;

  // 두 구현체를 빈으로 주입 받기 (필드 new 제거)
  @Autowired CommentCommandRepositoryImpl repository1;
  @Autowired CommentCommandRepositoryImpl3 repository2;

  @Nested
  @DisplayName("Insert Concurrency")
  class ConcurrencyTest {

    @Test
    @DisplayName("sibling_seq 동시성 테스트 - Root Comment")
    void insert_parent_comment_at_once() throws InterruptedException {
      StopWatch stopWatch = new StopWatch();
      stopWatch.start("RepoA");
      parentTest(repository1);
      stopWatch.stop();
      stopWatch.start("RepoB");
      parentTest(repository2);
      stopWatch.stop();
      log.info(stopWatch.prettyPrint());
    }

    @Test
    @DisplayName("sibling_seq 동시성 테스트 - Child Comment")
    void insert_child_comment_at_once() throws InterruptedException {
      StopWatch stopWatch = new StopWatch();
      stopWatch.start("RepoA");
      childParentTest(repository1);
      stopWatch.stop();
      stopWatch.start("RepoB");
      childParentTest(repository2);
      stopWatch.stop();
      log.info(stopWatch.prettyPrint());
    }

    private void parentTest(CommentCommandRepository repository) throws InterruptedException {
      PostEntity entity =
          GuestPostEntity.builder()
              .title("for test")
              .content("guest post")
              .viewCount(0L)
              .nickname("guest")
              .password("pass-1234")
              .board(BoardEmbed.builder().boardId(1L).build())
              .isDeleted(false)
              .build();
      PostEntity save = postEntityRepository.save(entity);
      Long postId = save.getId();
      ExecutorService pool = Executors.newFixedThreadPool(THREADS);
      CyclicBarrier startLine = new CyclicBarrier(THREADS);
      CountDownLatch done = new CountDownLatch(THREADS);

      List<Future<Long>> futures = new ArrayList<>(THREADS);

      for (int i = 0; i < THREADS; i++) {
        futures.add(
            pool.submit(
                () -> {
                  try {
                    startLine.await(1000, TimeUnit.MILLISECONDS);
                  } catch (Exception ignored) {
                    log.debug("setting error");
                  }
                  try {
                    Comment comment = Comment.write(guestParams(postId));
                    return repository.save(comment).getId();
                  } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                  } finally {
                    done.countDown();
                  }
                }));
      }

      done.await(5, TimeUnit.SECONDS);
      pool.shutdown();

      assertThat(futures.stream().filter(f -> !f.isDone()).count()).isEqualTo(0);

      Set<Integer> set = new HashSet<>();
      commentEntityRepository.findAll().stream()
          .forEach(
              c -> {
                assertThat(set.contains(c.getSiblingSeq())).isFalse();
                if (c.getPostId().equals(postId)) set.add(c.getSiblingSeq());
              });
      assertThat(set.size()).isEqualTo(THREADS);
    }

    private void childParentTest(CommentCommandRepository repository) throws InterruptedException {

      PostEntity entity =
          GuestPostEntity.builder()
              .title("for test")
              .content("guest post")
              .viewCount(0L)
              .nickname("guest")
              .password("pass-1234")
              .board(BoardEmbed.builder().boardId(1L).build())
              .isDeleted(false)
              .build();
      PostEntity save = postEntityRepository.save(entity);
      Long postId = save.getId();
      ExecutorService pool = Executors.newFixedThreadPool(THREADS);
      CyclicBarrier startLine = new CyclicBarrier(THREADS);
      CountDownLatch done = new CountDownLatch(THREADS);

      List<Future<Long>> futures = new ArrayList<>(THREADS);

      Comment parent = Comment.write(guestParams(postId));
      Comment saved = repository.save(parent);
      Long parentId = saved.getId();

      for (int i = 0; i < THREADS; i++) {
        futures.add(
            pool.submit(
                () -> {
                  try {
                    startLine.await(1000, TimeUnit.MILLISECONDS);
                  } catch (Exception ignored) {
                    log.debug("setting error");
                  }
                  try {
                    Comment comment = Comment.write(guestParams(postId, parentId));
                    return repository.save(comment).getId();
                  } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                  } finally {
                    done.countDown();
                  }
                }));
      }

      done.await(5, TimeUnit.SECONDS);
      pool.shutdown();

      Set<Integer> set = new HashSet<>();
      List<CommentEntity> all = commentEntityRepository.findAll();

      assertThat(futures.stream().filter(f -> !f.isDone()).count()).isEqualTo(0);

      all.stream()
          .filter(c -> c.getParentId().equals(parentId))
          .forEach(
              c -> {
                assertThat(set.contains(c.getSiblingSeq())).isFalse();
                if (c.getPostId().equals(postId)) set.add(c.getSiblingSeq());
              });

      assertThat(set.size()).isEqualTo(THREADS);
    }
  }

  private CommentCreateParams guestParams(Long postId) {
    return CommentCreateParams.builder()
        .postId(postId)
        .parentCommentId(null)
        .content("hello-guest")
        .commentType(CommentType.GUEST)
        .nickname("게스트닉")
        .password("pass-1234")
        .build();
  }

  private CommentCreateParams guestParams(Long postId, Long parentId) {
    return CommentCreateParams.builder()
        .postId(postId)
        .parentCommentId(parentId)
        .content("hello-guest-child")
        .commentType(CommentType.GUEST)
        .nickname("child")
        .password("pass-1234")
        .build();
  }
}
