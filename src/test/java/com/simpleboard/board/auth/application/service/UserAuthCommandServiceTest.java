package com.simpleboard.board.auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.simpleboard.board.auth.application.dto.request.EmailMemberRegisterCommand;
import com.simpleboard.board.auth.application.exception.UserNotFoundException;
import com.simpleboard.board.auth.domain.auth.dto.request.RegisterParams;
import com.simpleboard.board.auth.domain.auth.entity.UserAuth;
import com.simpleboard.board.auth.domain.auth.repository.UserAuthCommandRepository;
import com.simpleboard.board.auth.domain.auth.vo.RegisterType;
import com.simpleboard.board.auth.domain.auth.vo.UserState;
import com.simpleboard.board.auth.domain.common.vo.Role;
import com.simpleboard.board.auth.domain.token.dto.response.VerifyTokenInfo;
import com.simpleboard.board.auth.domain.token.service.TokenDomainService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class UserAuthCommandServiceTest {

    private TokenDomainService tokenDomainService;
    private UserAuthCommandRepository authCommandRepository;
    private UserAuthCommandService service;

    @BeforeEach
    void setUp() {
        tokenDomainService = mock(TokenDomainService.class);
        authCommandRepository = mock(UserAuthCommandRepository.class);
        service = new UserAuthCommandServiceImpl(tokenDomainService, authCommandRepository);
    }

    @Test
    @DisplayName("Email 회원가입 성공: Verify 토큰 파싱 → EmailUserAuth 생성/저장")
    void emailMemberRegister_Success_Test() {
        // given
        String emailTokenRaw = "emailToken";
        String nicknameTokenRaw = "nicknameToken";
        String email = "user@example.com";
        String nickname = "snoopy";
        String password = "pw1234";

        when(tokenDomainService.validateAndParseVerifyToken(emailTokenRaw))
                .thenReturn(VerifyTokenInfo.builder().subject(email).build());
        when(tokenDomainService.validateAndParseVerifyToken(nicknameTokenRaw))
                .thenReturn(VerifyTokenInfo.builder().subject(nickname).build());

        // save가 무엇을 반환하든 상관없지만, 자연스럽게 save 인자로 받은 것을 그대로 반환
        when(authCommandRepository.save(any(UserAuth.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EmailMemberRegisterCommand cmd =
                EmailMemberRegisterCommand.builder()
                        .emailTokenRaw(emailTokenRaw)
                        .nicknameTokenRaw(nicknameTokenRaw)
                        .password(password)
                        .gender("M")
                        .birthYear(1997)
                        .build();

        // when
        service.emailMemberRegister(cmd);

        // then
        verify(tokenDomainService).validateAndParseVerifyToken(emailTokenRaw);
        verify(tokenDomainService).validateAndParseVerifyToken(nicknameTokenRaw);

        ArgumentCaptor<UserAuth> captor = ArgumentCaptor.forClass(UserAuth.class);
        verify(authCommandRepository).save(captor.capture());

        UserAuth saved = captor.getValue();
        // 생성된 Aggregate의 스냅샷 검증
        assertThat(saved.getRegisterType()).isEqualTo(RegisterType.EMAIL);
        assertThat(saved.getRole()).isEqualTo(Role.MEMBER);
        assertThat(saved.getUserState()).isEqualTo(UserState.ACTIVE);
        assertThat(saved.getLoginId()).isEqualTo(email);     // EmailUserAuth의 loginId는 email
        assertThat(saved.getPassword()).isEqualTo(password); // EmailUserAuth의 password
    }

    @Test
    @DisplayName("회원 탈퇴 성공: ACTIVE → DELETED 전이 및 저장 호출")
    void deactivateAccount_Success_Test() {
        // given
        Long memberId = 42L;
        UserAuth active =
                UserAuth.register(
                        RegisterParams.builder()
                                .registerType(RegisterType.EMAIL)
                                .role(Role.MEMBER)
                                .email("user@example.com")
                                .password("pw")
                                .build());
        when(authCommandRepository.findById(memberId)).thenReturn(Optional.of(active));
        when(authCommandRepository.save(any(UserAuth.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        service.deactivateAccount(memberId);

        // then
        assertThat(active.getUserState()).isEqualTo(UserState.DELETED);
        verify(authCommandRepository).save(active);
    }

    @Test
    @DisplayName("회원 탈퇴 실패: 대상 사용자를 찾을 수 없으면 UserNotFoundException")
    void deactivateAccount_NotFound_Fail_Test() {
        // given
        Long memberId = 99L;
        when(authCommandRepository.findById(memberId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> service.deactivateAccount(memberId))
                .isInstanceOf(UserNotFoundException.class);
        verify(authCommandRepository, never()).save(any());
    }
}
