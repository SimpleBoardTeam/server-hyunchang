package com.simpleboard.board.member.domain;

import com.simpleboard.board.member.domain.exception.DuplicatedNicknameException;
import com.simpleboard.board.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class NicknameDuplicationChecker {

  private final MemberRepository memberRepository;

  public NicknameDuplicationChecker(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public void checkDuplicated(String nickname) {
    if (memberRepository.existsByNickname(nickname)) {
      throw new DuplicatedNicknameException();
    }
  }
}
