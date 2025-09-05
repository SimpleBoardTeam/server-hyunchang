package com.simpleboard.board.member.infrastructure.jpa.repository;

import com.simpleboard.board.member.domain.Nickname;
import com.simpleboard.board.member.infrastructure.jpa.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Long> {
  boolean existsByNickname(Nickname nickname);
}
