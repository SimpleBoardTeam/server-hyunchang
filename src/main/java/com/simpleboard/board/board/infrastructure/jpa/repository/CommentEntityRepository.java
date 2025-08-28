package com.simpleboard.board.board.infrastructure.jpa.repository;

import com.simpleboard.board.board.infrastructure.jpa.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <b>Comment aggregate의 JPA 레포지토리</b>
 *
 * <p>Comment JPA 엔티티의 저장 및 조회 담당</p>
 */
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Long> {
    @Override
    Optional<CommentEntity> findById(Long aLong);

    CommentEntity save(CommentEntity commentEntity);
}
