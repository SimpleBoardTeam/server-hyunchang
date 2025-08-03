package com.simpleboard.board.member.domain.repository;

public interface UserRepository {
  Boolean existsByNickname(String nickname);
}
