package com.simpleboard.board.member.infrastructure.jpa.repository;

import com.simpleboard.board.member.infrastructure.jpa.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Long> {
  @Query(
      value = "select exists (select 1 from members where nickname = :nickname)",
      nativeQuery = true)
  boolean existsByNicknameValue(@Param("nickname") String nickname);
}
