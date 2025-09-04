package com.simpleboard.board.auth.infrastructure.jpa.repository;

import com.simpleboard.board.auth.domain.auth.entity.UserAuth;
import com.simpleboard.board.auth.domain.auth.repository.UserAuthCommandRepository;
import com.simpleboard.board.auth.infrastructure.jpa.converter.AuthEntityConverter;
import com.simpleboard.board.auth.infrastructure.jpa.entity.EmailUserAuthEntity;
import com.simpleboard.board.auth.infrastructure.jpa.entity.OAuthUserAuthEntity;
import com.simpleboard.board.auth.infrastructure.jpa.entity.UserAuthEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthCommandRepositoryImpl implements UserAuthCommandRepository {

  private final UserAuthEntityRepository jpaRepository;
  private final AuthEntityConverter converter;

  @Override
  public UserAuth save(UserAuth userAuth) {
    UserAuthEntity saved = jpaRepository.save(converter.toJpaEntity(userAuth));
    return converter.toDomainEntity(saved);
  }

  @Override
  public Optional<UserAuth> findById(Long id) {
    Optional<UserAuthEntity> optional = jpaRepository.findById(id);
    if (optional.isEmpty()) return Optional.empty();
    UserAuthEntity userAuthEntity = optional.get();
    return Optional.of(converter.toDomainEntity(userAuthEntity));
  }

  @Override
  public Optional<UserAuth> findByEmail(String email) {
    Optional<EmailUserAuthEntity> optional = jpaRepository.findByEmail(email);
    if (optional.isEmpty()) return Optional.empty();
    UserAuthEntity userAuthEntity = optional.get();
    return Optional.of(converter.toDomainEntity(userAuthEntity));
  }

  @Override
  public Optional<UserAuth> findByOAuthId(String oauthId) {
    Optional<OAuthUserAuthEntity> optional = jpaRepository.findByOAuthId(oauthId);
    if (optional.isEmpty()) return Optional.empty();
    UserAuthEntity userAuthEntity = optional.get();
    return Optional.of(converter.toDomainEntity(userAuthEntity));
  }
}
