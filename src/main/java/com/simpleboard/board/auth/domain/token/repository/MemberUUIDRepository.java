package com.simpleboard.board.auth.domain.token.repository;

/**
 * <b>MemberUUIDRepository</b> Repository.
 *
 * <p>멤버 식별자를 토큰 subject로 사용하는 UUID로 매핑/조회
 *
 * @domain repository
 * @since 1.0
 */
public interface MemberUUIDRepository {
  /**
   * <b>UUID 생성/획득</b>
   *
   * <p>memberId에 매핑된 UUID가 없으면 생성, 존재하면 반환
   *
   * @since 1.0
   */
  String createOrGetUUID(Long memberId);

  /**
   * <b>memberId 조회</b>
   *
   * <p>subject(UUID)로부터 원 멤버 식별자 조회
   *
   * @since 1.0
   */
  Long getMemberId(String UUID);
}
