package com.simpleboard.board.auth.domain.auth.repository;

import com.simpleboard.board.auth.domain.auth.entity.UserAuth;
import java.util.Optional;

/**
 * UserAuth 저장소 포트.
 *
 * <p>Authentication aggregate에 대한 조회/저장 수행
 *
 * @domain repository-port
 */
public interface AuthCommandRepository {
  UserAuth save(UserAuth userAuth);

  Optional<UserAuth> findById(Long id);

  Optional<UserAuth> findByEmail(String email);

  Optional<UserAuth> findByOAuthId(String oauthId);
}
