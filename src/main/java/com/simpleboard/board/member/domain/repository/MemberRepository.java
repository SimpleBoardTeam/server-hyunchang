package com.simpleboard.board.member.domain.repository;

import com.simpleboard.board.member.domain.Member;
import java.util.Optional;

public interface MemberRepository {
  Boolean existsByNickname(String nickname);

  Optional<Member> findById(Long memberId);

  void save(Member member);
}
