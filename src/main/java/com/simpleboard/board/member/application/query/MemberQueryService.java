package com.simpleboard.board.member.application.query;

import com.simpleboard.board.member.application.exception.MemberNotFoundException;
import com.simpleboard.board.member.application.query.dto.MemberFullView;
import com.simpleboard.board.member.application.query.dto.MemberProfileResponse;
import com.simpleboard.board.member.application.query.repository.MemberQueryRepository;

/**
 *
 *
 * <h2>MemberQueryService</h2>
 *
 * <p>회원 도메인에 대한 조회 유스케이스를 처리하는 Application Service.
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
    MemberFullView memberFullView =
        memberQueryRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

    return MemberProfileResponse.from(memberFullView);
  }
}
