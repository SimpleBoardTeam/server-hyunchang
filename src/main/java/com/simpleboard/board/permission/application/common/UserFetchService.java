package com.simpleboard.board.permission.application.common;

/**
 * UserFetch 유스케이스.
 *
 * @domain application-service
 * @transactional
 */
public interface UserFetchService {
  Long getUserIdByNickname(String nickname);
}
