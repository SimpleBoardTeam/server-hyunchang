package com.simpleboard.board.member.application.query.repository;

import com.simpleboard.board.member.domain.Member;
import java.util.Optional;

public interface MemberQueryRepository {
  boolean existsByNickname(String nickname);

  Optional<Member> findById(Long memberId);
}
