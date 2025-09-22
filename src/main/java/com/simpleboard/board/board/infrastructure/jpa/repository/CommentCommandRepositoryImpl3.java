package com.simpleboard.board.board.infrastructure.jpa.repository;

import com.simpleboard.board.board.domain.comment.entity.Comment;
import com.simpleboard.board.board.domain.comment.repository.CommentCommandRepository;
import com.simpleboard.board.board.infrastructure.jpa.converter.CommentConverter;
import com.simpleboard.board.board.infrastructure.jpa.entity.CommentEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>Board B.C Comment Command Repository 구현체</b>
 *
 * <p>Domain entity <-> JPA entity 간의 변환을 캡슐화하여 내부적으로 처리
 *
 * <p>Domain entity의 저장, 수정, 조회 수행
 */
@Component
@RequiredArgsConstructor
public class CommentCommandRepositoryImpl3 implements CommentCommandRepository {

  private static final Logger log = LoggerFactory.getLogger(CommentCommandRepositoryImpl3.class);
  private final PostEntityRepository postEntityRepository;
  private final CommentEntityRepository commentEntityRepository;
  private final CommentConverter converter;

  private final Integer RETRY_LIMIT = 10;

  @Override
  @Transactional
  public Comment save(Comment comment) {
    if (comment.getId() != null) {
      CommentEntity saved = commentEntityRepository.save(converter.toJpaEntity(comment));
      return converter.toDomainEntity(saved);
    }
    CommentEntity entity = converter.toJpaEntity(comment);

    if (entity.getParentId() == null) {
      throw new DataIntegrityViolationException("Parent id is null");
    } else if (entity.getParentId().equals(0L)) {
      // 1. root 댓글이면 post를 lock
      Long parentId = postEntityRepository.lockById(entity.getPostId()).orElseThrow();
      log.info("{}", parentId);
    } else {
      // 2. 대댓글이면 parent comment를 lock
      commentEntityRepository.lockById(entity.getParentId());
    }

    try {
      Integer lastSeq = commentEntityRepository.maxSiblingSeqByParentId(entity.getParentId());
      lastSeq = lastSeq != null ? lastSeq : 0;

      entity.setSiblingSeq(lastSeq + 1);

      return converter.toDomainEntity(commentEntityRepository.save(entity));
    } catch (DataIntegrityViolationException e) {
      log.info("Parent ID: {}  Attempt {} failed", comment.getParentId(), RETRY_LIMIT);
      throw new DataIntegrityViolationException("Parent ID: " + comment.getParentId() + " failed");
    }
  }

  @Override
  public Optional<Comment> findById(Long id) {
    CommentEntity comment = commentEntityRepository.findById(id).orElse(null);
    if (comment == null) return Optional.empty();
    return Optional.of(converter.toDomainEntity(comment));
  }
}
