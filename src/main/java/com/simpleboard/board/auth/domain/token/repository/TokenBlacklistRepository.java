package com.simpleboard.board.auth.domain.token.repository;

import java.time.Instant;

/**
 * <b>TokenBlacklistRepository</b> Repository.
 *
 * <p>리프레시 토큰의 블랙리스트 등록/조회
 *
 * @domain repository
 * @since 1.0
 */
public interface TokenBlacklistRepository {
  /**
   * <b>블랙리스트 등록</b>
   *
   * <p>토큰 식별자와 만료 시각을 저장
   *
   * @since 1.0
   */
  void save(String tokenId, Instant exp);

  /**
   * <b>블랙리스트 존재 확인</b>
   *
   * <p>토큰 식별자의 블랙리스트 등록 여부 확인
   *
   * @since 1.0
   */
  boolean exists(String tokenId);
}
