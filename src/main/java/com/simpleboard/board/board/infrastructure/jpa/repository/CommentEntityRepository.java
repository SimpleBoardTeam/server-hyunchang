package com.simpleboard.board.board.infrastructure.jpa.repository;

import com.simpleboard.board.board.infrastructure.jpa.entity.CommentEntity;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <b>Comment aggregate의 JPA 레포지토리</b>
 *
 * <p>Comment JPA 엔티티의 저장 및 조회 담당
 */
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select c from CommentEntity c where c.id = :id")
  Optional<CommentEntity> lockById(@Param("id") Long id);

  CommentEntity save(CommentEntity commentEntity);

  @Query(
      value =
          "select sibling_seq from comment where parent_id = :parentId order by sibling_seq desc limit 1",
      nativeQuery = true)
  Integer maxSiblingSeqByParentId(@Param("parentId") Long parentId);
}
