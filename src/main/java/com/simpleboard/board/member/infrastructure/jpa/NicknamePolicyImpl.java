package com.simpleboard.board.member.infrastructure.jpa;

import com.simpleboard.board.member.domain.Nickname;
import com.simpleboard.board.member.domain.NicknamePolicy;
import com.simpleboard.board.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NicknamePolicyImpl implements NicknamePolicy {

  private final MemberRepository memberRepository;

  @Override
  public boolean isUnique(Nickname nickname) {
    return !memberRepository.existsByNickname(nickname.getValue());
  }
}
