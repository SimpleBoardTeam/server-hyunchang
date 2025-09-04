package com.simpleboard.board.member.domain;

import com.simpleboard.board.member.domain.exception.DuplicatedNicknameException;
import com.simpleboard.board.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 닉네임 중복 여부를 검사하는 도메인 정책.
 *
 * <p>회원 가입 시 닉네임의 유일성을 보장하기 위해 사용
 *
 * @domain domain-policy
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class NicknamePolicy {

  private final MemberRepository memberRepository;

  void ensureUnique(Nickname nickname) {
    if (memberRepository.existsByNickname(nickname)) throw new DuplicatedNicknameException();
  }
}
