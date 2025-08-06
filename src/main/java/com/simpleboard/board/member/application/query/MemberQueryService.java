package com.simpleboard.board.member.application.query;

import com.simpleboard.board.member.application.exception.MemberNotFoundException;
import com.simpleboard.board.member.application.query.dto.MemberProfileResponse;
import com.simpleboard.board.member.application.query.repository.MemberQueryRepository;
import com.simpleboard.board.member.domain.Member;

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
