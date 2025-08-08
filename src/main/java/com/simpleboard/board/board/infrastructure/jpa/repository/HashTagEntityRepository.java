package com.simpleboard.board.board.infrastructure.jpa.repository;

import com.simpleboard.board.board.infrastructure.jpa.entity.HashTagEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagEntityRepository extends JpaRepository<HashTagEntity, Long> {
  Optional<HashTagEntity> findByTag(String tag);

  HashTagEntity save(HashTagEntity hashTagEntity);
}
