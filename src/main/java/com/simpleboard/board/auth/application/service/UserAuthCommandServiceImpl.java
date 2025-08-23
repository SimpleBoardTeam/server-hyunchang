package com.simpleboard.board.auth.application.service;

import com.simpleboard.board.auth.application.dto.request.EmailMemberRegisterCommand;
import com.simpleboard.board.auth.application.dto.request.OAuthMemberRegisterCommand;
import com.simpleboard.board.auth.application.exception.UserNotFoundException;
import com.simpleboard.board.auth.domain.auth.dto.request.RegisterParams;
import com.simpleboard.board.auth.domain.auth.entity.UserAuth;
import com.simpleboard.board.auth.domain.auth.repository.UserAuthCommandRepository;
import com.simpleboard.board.auth.domain.auth.vo.RegisterType;
import com.simpleboard.board.auth.domain.common.vo.Role;
import com.simpleboard.board.auth.domain.token.service.TokenDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthCommandServiceImpl implements UserAuthCommandService {

  private final TokenDomainService tokenDomainService;
  private final UserAuthCommandRepository authCommandRepository;

  @Override
  public void emailMemberRegister(EmailMemberRegisterCommand command) {
    String email =
        tokenDomainService.validateAndParseVerifyToken(command.emailTokenRaw()).subject();
    String nickname =
        tokenDomainService.validateAndParseVerifyToken(command.nicknameTokenRaw()).subject();

    UserAuth register =
        UserAuth.register(
            RegisterParams.builder()
                .registerType(RegisterType.EMAIL)
                .role(Role.MEMBER)
                .email(email)
                .password(command.password())
                .build());

    UserAuth saved = authCommandRepository.save(register);
    // TODO: EmailUserRegistered Event
  }

  @Override
  public void oAuthMemberRegister(OAuthMemberRegisterCommand command) {
    // TODO: implements OAuth user register
  }

  @Override
  public void deactivateAccount(Long memberId) {
    UserAuth userAuth =
        authCommandRepository.findById(memberId).orElseThrow(UserNotFoundException::new);
    userAuth.delete();
    authCommandRepository.save(userAuth);
    // TODO: UserDeleted Event
  }
}
