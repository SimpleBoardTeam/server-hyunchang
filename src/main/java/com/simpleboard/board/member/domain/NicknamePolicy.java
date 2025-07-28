package com.simpleboard.board.member.domain;

public interface NicknamePolicy {
  void checkFormat(String nickname);

  void checkDuplicate(String nickname);
}
