package com.simpleboard.board.member.domain.repository;

import com.simpleboard.board.member.domain.Member;
import com.simpleboard.board.member.domain.Nickname;
import java.util.Optional;

public interface MemberRepository {
  Boolean existsByNickname(Nickname nickname);

  Optional<Member> findById(Long memberId);

  void save(Member member);
}
