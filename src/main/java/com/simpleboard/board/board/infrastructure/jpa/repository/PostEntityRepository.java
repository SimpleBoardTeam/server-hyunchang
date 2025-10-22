package com.simpleboard.board.board.infrastructure.jpa.repository;

import com.simpleboard.board.board.infrastructure.jpa.entity.PostEntity;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {
  Optional<PostEntity> findById(Long aLong);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT p.id FROM PostEntity p WHERE p.id=:id")
  Optional<Long> lockById(@Param("id") Long id);

  PostEntity save(PostEntity postEntity);
}
