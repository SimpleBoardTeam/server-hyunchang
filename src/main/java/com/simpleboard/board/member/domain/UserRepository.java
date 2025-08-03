package com.simpleboard.board.member.domain;

public interface UserRepository {
  Boolean existsByNickname(String nickname);
}
