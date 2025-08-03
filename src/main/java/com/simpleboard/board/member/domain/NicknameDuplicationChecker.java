package com.simpleboard.board.member.domain;

import com.simpleboard.board.member.domain.exception.DuplicatedNicknameException;

public class NicknameDuplicationChecker {

  private final UserRepository userRepository;

  public NicknameDuplicationChecker(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void checkDuplicated(String nickname) {
    if (userRepository.existsByNickname(nickname)) {
      throw new DuplicatedNicknameException();
    }
  }
}
