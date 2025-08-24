package com.simpleboard.board.member.domain;

import com.simpleboard.board.member.domain.exception.DuplicatedNicknameException;
import com.simpleboard.board.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Component;

/**
 * 닉네임 중복 여부를 검사하는 도메인 서비스.
 *
 * <p>회원 가입 시 닉네임의 유일성을 보장하기 위해 사용되며, 내부 도메인(MemberRepository)에만 의존하고 외부 BC 의존은 없다.
 *
 * @domain domain-service
 * @since 1.0
 */
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
