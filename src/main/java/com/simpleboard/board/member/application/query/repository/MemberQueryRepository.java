package com.simpleboard.board.member.application.query.repository;

import com.simpleboard.board.member.domain.Member;
import java.util.Optional;

/**
 * <h2>MemberQueryRepository</h2>
 *
 * <p>회원 도메인에 대한 조회 기능을 제공하는 Repository Port.</p>
 *
 * @domain repository-port
 */
public interface MemberQueryRepository {
  boolean existsByNickname(String nickname);

  Optional<Member> findById(Long memberId);
}
