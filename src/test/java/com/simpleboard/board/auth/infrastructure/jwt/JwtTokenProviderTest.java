package com.simpleboard.board.auth.infrastructure.jwt;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.auth.domain.common.vo.Role;
import com.simpleboard.board.auth.domain.token.vo.*;
import com.simpleboard.board.auth.infrastructure.jwt.exception.TokenExpiredException;
import com.simpleboard.board.auth.infrastructure.jwt.exception.TokenParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private final String secret = "01234567890123456789012345678901"; // 32-byte key
    private final String issuer = "test-issuer";
    private final long skewSeconds = 5L;
    private final JwtTokenProvider provider = new JwtTokenProvider(secret, issuer, skewSeconds);

    @Test
    @DisplayName("ACCESS 토큰 발급 및 검증 테스트")
    void issueAndVerifyAccessToken_Succeed_Test() {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofHours(1));

        TokenClaims claims =
                TokenClaims.builder()
                        .tokenId("access-id")
                        .tokenPurpose(TokenPurpose.ACCESS)
                        .role(Role.MEMBER)
                        .subject("sub-access")
                        .audience("aud-a")
                        .issuer(issuer)
                        .issueAt(now)
                        .expiredAt(expiry)
                        .build();

        Token token = provider.issueToken(claims);
        TokenClaims parsed = provider.verifyAndParseToken(token.raw(), Date.from(now));

        assertThat(parsed.tokenPurpose()).isEqualTo(TokenPurpose.ACCESS);
        assertThat(parsed.role()).isEqualTo(Role.MEMBER);
        assertThat(parsed.verifyPurpose()).isNull();
        assertThat(parsed.audience()).isEqualTo("aud-a");
        // round-trip 필드 확인
        assertThat(parsed.issuer()).isEqualTo(issuer);
        assertThat(parsed.subject()).isEqualTo("sub-access");
        assertThat(parsed.tokenId()).isEqualTo("access-id");
        assertThat(now.minusMillis(parsed.issueAt().toEpochMilli()).toEpochMilli()).isLessThanOrEqualTo(skewSeconds * 1000);
        assertThat(expiry.minusMillis(parsed.expiredAt().toEpochMilli()).toEpochMilli()).isLessThanOrEqualTo(skewSeconds * 1000);
    }

    @Test
    @DisplayName("REFRESH 토큰 발급 및 검증 테스트")
    void issueAndVerifyRefreshToken_Succeed_Test() {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofDays(7));

        TokenClaims claims =
                TokenClaims.builder()
                        .tokenId("refresh-id")
                        .tokenPurpose(TokenPurpose.REFRESH)
                        .role(Role.ADMIN)
                        .subject("sub-refresh")
                        .audience("aud-r")
                        .issuer(issuer)
                        .issueAt(now)
                        .expiredAt(expiry)
                        .build();

        Token token = provider.issueToken(claims);
        TokenClaims parsed = provider.verifyAndParseToken(token.raw(), Date.from(now));

        assertThat(parsed.tokenPurpose()).isEqualTo(TokenPurpose.REFRESH);
        assertThat(parsed.role()).isEqualTo(Role.ADMIN);
        assertThat(parsed.verifyPurpose()).isNull();
        assertThat(parsed.audience()).isEqualTo("aud-r");
        assertThat(parsed.tokenId()).isEqualTo("refresh-id");
    }

    @Test
    @DisplayName("VERIFY 토큰 발급 및 검증 테스트")
    void issueAndVerifyVerifyToken_Succeed_Test() {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMinutes(30));

        TokenClaims claims =
                TokenClaims.builder()
                        .tokenId("verify-id")
                        .tokenPurpose(TokenPurpose.VERIFY)
                        .verifyPurpose(VerifyPurpose.PASSWORD)
                        .subject("sub-verify")
                        .audience("aud-v")
                        .issuer(issuer)
                        .issueAt(now)
                        .expiredAt(expiry)
                        .build();

        Token token = provider.issueToken(claims);
        TokenClaims parsed = provider.verifyAndParseToken(token.raw(), Date.from(now));

        assertThat(parsed.tokenPurpose()).isEqualTo(TokenPurpose.VERIFY);
        assertThat(parsed.verifyPurpose()).isEqualTo(VerifyPurpose.PASSWORD);
        assertThat(parsed.role()).isNull(); // VERIFY는 role 미포함
        assertThat(parsed.audience()).isEqualTo("aud-v");
        assertThat(parsed.tokenId()).isEqualTo("verify-id");
    }

    @Test
    @DisplayName("만료된 JWT 검증 시 TokenExpiredException 발생")
    void verifyExpiredToken_ThrowsTokenExpiredException_Test() {
        Instant past = Instant.now().minus(Duration.ofHours(1));

        TokenClaims claims =
                TokenClaims.builder()
                        .tokenId("expired-id")
                        .tokenPurpose(TokenPurpose.ACCESS)
                        .role(Role.MEMBER)
                        .subject("sub-expired")
                        .audience("aud-exp")
                        .issuer(issuer)
                        .issueAt(past.minus(Duration.ofHours(1)))
                        .expiredAt(past)
                        .build();

        String raw = provider.issueToken(claims).raw();

        assertThatThrownBy(() -> provider.verifyAndParseToken(raw, Date.from(Instant.now())))
                .isInstanceOf(TokenExpiredException.class);
    }

    @Test
    @DisplayName("만료시각이 now와 정확히 같은 경우: TokenExpiredException 발생")
    void expirationEqualsNow_AllowsParsing_CurrentImplementation() {
        Instant now = Instant.now();
        // verifyAndParseToken 내부 만료 체크는 expiration.before(now)만 검사 → equals(now)는 허용
        TokenClaims claims =
                TokenClaims.builder()
                        .tokenId("eq-now-id")
                        .tokenPurpose(TokenPurpose.ACCESS)
                        .role(Role.MEMBER)
                        .subject("sub-eq")
                        .audience("aud-eq")
                        .issuer(issuer)
                        .issueAt(now.minusSeconds(1))
                        .expiredAt(now) // 경계
                        .build();

        String raw = provider.issueToken(claims).raw();
        assertThatThrownBy(() -> provider.verifyAndParseToken(raw, Date.from(now))).isInstanceOf(TokenExpiredException.class);
    }

    @Test
    @DisplayName("서명 위변조 시 TokenParseException 발생")
    void verifyTamperedToken_ThrowsTokenParseException_Test() {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMinutes(5));

        TokenClaims claims =
                TokenClaims.builder()
                        .tokenId("sig-id")
                        .tokenPurpose(TokenPurpose.ACCESS)
                        .role(Role.MEMBER)
                        .subject("sub-sig")
                        .audience("aud-sig")
                        .issuer(issuer)
                        .issueAt(now)
                        .expiredAt(expiry)
                        .build();

        String raw = provider.issueToken(claims).raw() + "tamper";

        assertThatThrownBy(() -> provider.verifyAndParseToken(raw, Date.from(now)))
                .isInstanceOf(TokenParseException.class);
    }

    @Test
    @DisplayName("발급자(issuer) 불일치 시 TokenParseException 발생")
    void verifyWrongIssuer_ThrowsTokenParseException_Test() {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMinutes(5));

        TokenClaims claimsOtherIssuer =
                TokenClaims.builder()
                        .tokenId("iss-id")
                        .tokenPurpose(TokenPurpose.ACCESS)
                        .role(Role.MEMBER)
                        .subject("sub-iss")
                        .audience("aud-iss")
                        .issuer("other-issuer") // 토큰은 other-issuer로 발급
                        .issueAt(now)
                        .expiredAt(expiry)
                        .build();

        String raw = provider.issueToken(claimsOtherIssuer).raw();

        // parser는 requireIssuer(issuer)를 강제 → 파싱 실패는 TokenParseException
        assertThatThrownBy(() -> provider.verifyAndParseToken(raw, Date.from(now)))
                .isInstanceOf(TokenParseException.class);
    }

    @Test
    @DisplayName("다른 secret으로 서명된 토큰 → TokenParseException")
    void verifyWrongSecret_ThrowsTokenParseException_Test() {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMinutes(5));

        TokenClaims claims =
                TokenClaims.builder()
                        .tokenId("wrong-secret-id")
                        .tokenPurpose(TokenPurpose.ACCESS)
                        .role(Role.MEMBER)
                        .subject("sub-ws")
                        .audience("aud-ws")
                        .issuer(issuer)
                        .issueAt(now)
                        .expiredAt(expiry)
                        .build();

        // 다른 secret으로 서명
        String otherSecret = "abcdefghijabcdefghijabcdefghijab"; // 32-byte
        JwtTokenProvider otherProvider = new JwtTokenProvider(otherSecret, issuer, skewSeconds);
        String rawOther = otherProvider.issueToken(claims).raw();

        assertThatThrownBy(() -> provider.verifyAndParseToken(rawOther, Date.from(now)))
                .isInstanceOf(TokenParseException.class);
    }

    @Test
    @DisplayName("VERIFY 토큰은 role 클레임을 무시한다")
    void verifyToken_IgnoresRoleClaimEvenIfProvided() {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMinutes(10));

        // role을 억지로 세팅해도, JwtTokenProvider.issueToken에서 VERIFY면 role 클레임을 넣지 않음
        TokenClaims claims =
                TokenClaims.builder()
                        .tokenId("verify-role-id")
                        .tokenPurpose(TokenPurpose.VERIFY)
                        .verifyPurpose(VerifyPurpose.EMAIL)
                        .role(Role.ADMIN) // 의미 없음
                        .subject("sub-vr")
                        .audience("aud-vr")
                        .issuer(issuer)
                        .issueAt(now)
                        .expiredAt(expiry)
                        .build();

        String raw = provider.issueToken(claims).raw();
        TokenClaims parsed = provider.verifyAndParseToken(raw, Date.from(now));

        assertThat(parsed.tokenPurpose()).isEqualTo(TokenPurpose.VERIFY);
        assertThat(parsed.verifyPurpose()).isEqualTo(VerifyPurpose.EMAIL);
        assertThat(parsed.role()).isNull();
    }

    @Test
    @DisplayName("VERIFY 토큰에서 verifyPurpose가 누락된 경우 null로 파싱된다")
    void verifyToken_NullPurpose_ParsesAsNull() {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMinutes(10));

        TokenClaims claims =
                TokenClaims.builder()
                        .tokenId("verify-null-purpose")
                        .tokenPurpose(TokenPurpose.VERIFY)
                        .verifyPurpose(null)
                        .subject("sub-v-null")
                        .audience("aud-v-null")
                        .issuer(issuer)
                        .issueAt(now)
                        .expiredAt(expiry)
                        .build();

        String raw = provider.issueToken(claims).raw();
        TokenClaims parsed = provider.verifyAndParseToken(raw, Date.from(now));

        assertThat(parsed.tokenPurpose()).isEqualTo(TokenPurpose.VERIFY);
        assertThat(parsed.verifyPurpose()).isNull();
        assertThat(parsed.role()).isNull();
    }
}
