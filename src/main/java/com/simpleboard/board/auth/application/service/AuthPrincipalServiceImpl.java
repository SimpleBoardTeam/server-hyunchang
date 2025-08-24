package com.simpleboard.board.auth.application.service;

import com.simpleboard.board.auth.application.exception.UserDisabledException;
import com.simpleboard.board.auth.application.exception.UserNotFoundException;
import com.simpleboard.board.auth.domain.auth.entity.UserAuth;
import com.simpleboard.board.auth.domain.auth.repository.UserAuthCommandRepository;
import com.simpleboard.board.auth.domain.auth.vo.UserState;
import com.simpleboard.board.auth.domain.common.vo.Role;
import com.simpleboard.board.auth.domain.token.dto.request.LoginTokenIssueParam;
import com.simpleboard.board.auth.domain.token.dto.response.LoginTokenInfo;
import com.simpleboard.board.auth.domain.token.service.TokenDomainService;
import com.simpleboard.board.auth.domain.token.vo.TokenPair;
import com.simpleboard.board.auth.domain.token.vo.TokenPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthPrincipalServiceImpl implements AuthPrincipalService {
  private final TokenDomainService tokenDomainService;
  private final UserAuthCommandRepository userAuthCommandRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    UserAuth userAuth =
        userAuthCommandRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

    validateUserAuth(userAuth);
    return new AuthPrincipal(userAuth);
  }

  @Override
  public UserDetails loadUserByOAuthId(String oAuthId) {
    UserAuth userAuth =
        userAuthCommandRepository.findByOAuthId(oAuthId).orElseThrow(UserNotFoundException::new);

    validateUserAuth(userAuth);
    return new AuthPrincipal(userAuth);
  }

  @Override
  public UserDetails loadUserByTokenRaw(String tokenRaw) {
    LoginTokenInfo tokenInfo = tokenDomainService.validateAndParseLoginToken(tokenRaw);
    if (!tokenInfo.tokenPurpose().equals(TokenPurpose.ACCESS))
      throw new IllegalArgumentException("Invalid token");

    UserAuth userAuth =
        userAuthCommandRepository
            .findById(tokenInfo.memberId())
            .orElseThrow(UserNotFoundException::new);

    validateUserAuth(userAuth);
    return new AuthPrincipal(userAuth);
  }

  @Override
  public TokenPair issueTokenPair(AuthPrincipal principal) {

    Long memberId = principal.getUserId();
    Role role = principal.getRole();

    return tokenDomainService.issueLoginToken(
        LoginTokenIssueParam.builder().memberId(memberId).role(role).build());
  }

  private void validateUserAuth(UserAuth userAuth) {
    // TODO: UserState 추가시 검증 로직 필요
    if (!userAuth.getUserState().equals(UserState.ACTIVE)) {
      throw new UserDisabledException();
    }
  }
}
