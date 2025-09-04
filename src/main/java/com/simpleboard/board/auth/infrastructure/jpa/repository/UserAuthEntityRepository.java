package com.simpleboard.board.auth.infrastructure.jpa.repository;

import com.simpleboard.board.auth.infrastructure.jpa.entity.EmailUserAuthEntity;
import com.simpleboard.board.auth.infrastructure.jpa.entity.OAuthUserAuthEntity;
import com.simpleboard.board.auth.infrastructure.jpa.entity.UserAuthEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <b>UserAuthEntity의 JPA 레포지토리</b>
 *
 * <p>조건 검색시 자식 Entity 반환
 */
public interface UserAuthEntityRepository extends JpaRepository<UserAuthEntity, Long> {
  Optional<UserAuthEntity> findById(Long id);

  Optional<EmailUserAuthEntity> findByEmail(String email);

  Optional<OAuthUserAuthEntity> findByOAuthId(String oauthId);
}
