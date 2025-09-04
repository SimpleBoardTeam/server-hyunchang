package com.simpleboard.board.auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.simpleboard.board.auth.domain.token.dto.request.RefreshTokenRotationParam;
import com.simpleboard.board.auth.domain.token.dto.request.VerifyTokenIssueParam;
import com.simpleboard.board.auth.domain.token.exception.RefreshTokenInvalidException;
import com.simpleboard.board.auth.domain.token.service.TokenDomainService;
import com.simpleboard.board.auth.domain.token.vo.Token;
import com.simpleboard.board.auth.domain.token.vo.TokenPair;
import com.simpleboard.board.auth.domain.token.vo.VerifyPurpose;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class TokenApplicationServiceTest {

  private TokenDomainService tokenDomainService;
  private TokenApplicationService service;

  @BeforeEach
  void setUp() {
    tokenDomainService = mock(TokenDomainService.class);
    service = new TokenApplicationServiceImpl(tokenDomainService);
  }

  @Test
  @DisplayName("리프레시 로테이션 성공: 파라미터 위임 및 반환값 전달")
  void rotateRefreshToken_Success_Test() {
    // given
    String oldRaw = "old-refresh";
    TokenPair pair =
        TokenPair.builder()
            .access(Token.builder().raw("a").expiredAt(Instant.now().plusSeconds(10)).build())
            .refresh(Token.builder().raw("r").expiredAt(Instant.now().plusSeconds(100)).build())
            .build();
    when(tokenDomainService.rotateRefreshToken(any(RefreshTokenRotationParam.class)))
        .thenReturn(pair);

    // when
    TokenPair result = service.rotateRefreshToken(oldRaw);

    // then
    assertThat(result).isEqualTo(pair);

    ArgumentCaptor<RefreshTokenRotationParam> captor =
        ArgumentCaptor.forClass(RefreshTokenRotationParam.class);
    verify(tokenDomainService).rotateRefreshToken(captor.capture());
    assertThat(captor.getValue().oldRefreshRaw()).isEqualTo(oldRaw);
  }

  @Test
  @DisplayName("리프레시 로테이션 실패: 도메인 예외 전파")
  void rotateRefreshToken_Propagate_Exception_Test() {
    // given
    when(tokenDomainService.rotateRefreshToken(any(RefreshTokenRotationParam.class)))
        .thenThrow(new RefreshTokenInvalidException());

    // when & then
    assertThatThrownBy(() -> service.rotateRefreshToken("bad"))
        .isInstanceOf(RefreshTokenInvalidException.class);
  }

  @Test
  @DisplayName("Verify 토큰 발급 성공: 파라미터 위임 및 반환값 전달")
  void issueVerifyToken_Success_Test() {
    // given
    String subject = "email@example.com";
    VerifyPurpose purpose = VerifyPurpose.EMAIL;
    Token issued =
        Token.builder().raw("verifyRaw").expiredAt(Instant.now().plusSeconds(300)).build();
    when(tokenDomainService.issueVerifyToken(any(VerifyTokenIssueParam.class))).thenReturn(issued);

    // when
    Token result = service.issueVerifyToken(subject, purpose);

    // then
    assertThat(result).isEqualTo(issued);

    ArgumentCaptor<VerifyTokenIssueParam> captor =
        ArgumentCaptor.forClass(VerifyTokenIssueParam.class);
    verify(tokenDomainService).issueVerifyToken(captor.capture());
    assertThat(captor.getValue().subject()).isEqualTo(subject);
    assertThat(captor.getValue().purpose()).isEqualTo(purpose);
  }

  @Test
  @DisplayName("블랙리스트 등록 위임 확인")
  void enrollBlacklist_Delegate_Test() {
    // given
    String raw = "refreshRaw";

    // when
    service.enrollBlacklist(raw);

    // then
    verify(tokenDomainService).enrollBlacklist(raw);
  }

  @Test
  @DisplayName("토큰 탈취 차단 위임 확인: 블랙리스트 등록 + UUID 재발급 트리거")
  void blockSnatchedToken_Delegate_Test() {
    // given
    String raw = "stolen-refresh";

    // when
    service.blockSnatchedToken(raw);

    // then
    verify(tokenDomainService).blockTokenUser(raw);
  }
}
