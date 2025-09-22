package com.simpleboard.board.board.domain.comment.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.entity.Comment;
import com.simpleboard.board.board.domain.comment.vo.CommentType;
import com.simpleboard.board.board.infrastructure.jpa.converter.CommentConverter;
import com.simpleboard.board.board.infrastructure.jpa.entity.CommentEntity;
import com.simpleboard.board.board.infrastructure.jpa.repository.CommentCommandRepositoryImpl;
import com.simpleboard.board.board.infrastructure.jpa.repository.CommentEntityRepository;
import com.simpleboard.board.testconfig.JpaAuditTestConfig;
import java.util.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * <b>Repository 테스트</b>
 *
 * <p>H2 DB를 활용한 JPA 레포지토리 테스트
 */
@DataJpaTest
@ActiveProfiles("test-unit")
@Import({CommentCommandRepositoryImpl.class, CommentConverter.class, JpaAuditTestConfig.class})
class CommentCommandRepositoryConcurrencyTest {

  private static final Logger log =
      LoggerFactory.getLogger(CommentCommandRepositoryConcurrencyTest.class);
  @Autowired CommentCommandRepository repository;
  @Autowired CommentEntityRepository entityRepository;

  @Nested
  @DisplayName("Insert Concurrency")
  class ConcurrencyTest {

    private final int THREADS = 10;

    @Test
    @DisplayName("sibling_seq 동시성 테스트 - Root Comment")
    void insert_parent_comment_at_once() throws InterruptedException {

      ExecutorService pool = Executors.newFixedThreadPool(THREADS);
      CyclicBarrier startLine = new CyclicBarrier(THREADS);
      CountDownLatch done = new CountDownLatch(THREADS);

      List<Future<Long>> futures = new ArrayList<>(THREADS);

      for (int i = 0; i < THREADS; i++) {
        futures.add(
            pool.submit(
                () -> {
                  try {
                    startLine.await(100, TimeUnit.MILLISECONDS);
                  } catch (Exception ignored) {
                    log.debug("setting error");
                  }
                  try {
                    Comment comment = Comment.write(guestParams());
                    return repository.save(comment).getId();
                  } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                  } finally {
                    done.countDown();
                  }
                }));
      }

      done.await(1, TimeUnit.SECONDS);
      pool.shutdown();

      assertThat(futures.stream().filter(f -> !f.isDone()).count()).isEqualTo(0);

      Set<Integer> set = new HashSet<>();
      entityRepository.findAll().stream()
          .forEach(
              c -> {
                assertThat(set.contains(c.getSiblingSeq())).isFalse();
                set.add(c.getSiblingSeq());
              });
      assertThat(set.size()).isEqualTo(THREADS);
    }

    @Test
    @DisplayName("sibling_seq 동시성 테스트 - Child Comment")
    void insert_child_comment_at_once() throws InterruptedException {

      ExecutorService pool = Executors.newFixedThreadPool(THREADS);
      CyclicBarrier startLine = new CyclicBarrier(THREADS);
      CountDownLatch done = new CountDownLatch(THREADS);

      List<Future<Long>> futures = new ArrayList<>(THREADS);

      Comment parent = Comment.write(guestParams());
      Comment saved = repository.save(parent);
      Long parentId = saved.getId();

      for (int i = 0; i < THREADS; i++) {
        futures.add(
            pool.submit(
                () -> {
                  try {
                    startLine.await(100, TimeUnit.MILLISECONDS);
                  } catch (Exception ignored) {
                    log.debug("setting error");
                  }
                  try {
                    Comment comment = Comment.write(guestParams(parentId));
                    return repository.save(comment).getId();
                  } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                  } finally {
                    done.countDown();
                  }
                }));
      }

      done.await(1, TimeUnit.SECONDS);
      pool.shutdown();

      Set<Integer> set = new HashSet<>();
      List<CommentEntity> all = entityRepository.findAll();

      assertThat(futures.stream().filter(f -> !f.isDone()).count()).isEqualTo(0);

      all.stream()
          .filter(c -> c.getParentId().equals(parentId))
          .forEach(
              c -> {
                assertThat(set.contains(c.getSiblingSeq())).isFalse();
                set.add(c.getSiblingSeq());
              });

      assertThat(set.size()).isEqualTo(THREADS);
    }
  }

  private CommentCreateParams guestParams() {
    return CommentCreateParams.builder()
        .postId(100L)
        .parentCommentId(null)
        .content("hello-guest")
        .commentType(CommentType.GUEST)
        .nickname("게스트닉")
        .password("pass-1234")
        .build();
  }

  private CommentCreateParams guestParams(Long parentId) {
    return CommentCreateParams.builder()
        .postId(100L)
        .parentCommentId(parentId)
        .content("hello-guest-child")
        .commentType(CommentType.GUEST)
        .nickname("child")
        .password("pass-1234")
        .build();
  }
}
