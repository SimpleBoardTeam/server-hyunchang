package com.simpleboard.board.member.application.query;

import com.simpleboard.board.member.application.exception.MemberNotFoundException;
import com.simpleboard.board.member.application.query.dto.MemberProfileResponse;
import com.simpleboard.board.member.application.query.repository.MemberQueryRepository;
import com.simpleboard.board.member.domain.Member;

/**
 * <h2>MemberQueryService</h2>
 *
 * <p>회원 도메인에 대한 조회 유스케이스를 처리하는 Application Service.</p>
 *
 * @domain application-service
 * @transactional
 */
public class MemberQueryService {

  private final MemberQueryRepository memberQueryRepository;

  public MemberQueryService(MemberQueryRepository memberQueryRepository) {
    this.memberQueryRepository = memberQueryRepository;
  }

  public Boolean existsByNickname(String nickname) {
    return memberQueryRepository.existsByNickname(nickname);
  }

  public MemberProfileResponse getProfileById(Long memberId) {
    Member member =
        memberQueryRepository
            .findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

    return MemberProfileResponse.from(member);
  }
}
