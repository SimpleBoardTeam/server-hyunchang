package com.simpleboard.board.auth.infrastructure.jwt;

import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.Token.*;

import com.simpleboard.board.auth.domain.common.vo.Role;
import com.simpleboard.board.auth.domain.token.util.TokenProvider;
import com.simpleboard.board.auth.domain.token.vo.*;
import com.simpleboard.board.auth.infrastructure.jwt.exception.TokenExpiredException;
import com.simpleboard.board.auth.infrastructure.jwt.exception.TokenParseException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <b>JwtTokenProvider</b> Adapter.
 *
 * <p>io.jsonwebtoken(JJWT) 기반의 HS256 JWT 발급/검증 구현
 *
 * <ul>
 *   <li>Header/Claims 구성 및 서명
 *   <li>검증 시 issuer/서명/시간 스큐(clock skew) 적용
 * </ul>
 *
 * @domain infrastructure-adapter
 * @since 1.0
 */
@Component
public class JwtTokenProvider implements TokenProvider {

  private final SecretKey secret;
  private final String issuer;
  private final long skewSeconds;

  public JwtTokenProvider(
      @Value("${app.auth.jwt-hs256-secret}") String secret,
      @Value("${app.auth.issuer}") String issuer,
      @Value("${app.auth.skew-seconds:5}") long skewSeconds) {
    this.secret = Keys.hmacShaKeyFor(secret.getBytes());
    this.issuer = issuer;
    this.skewSeconds = skewSeconds;
  }

  @Override
  public Token issueToken(TokenClaims claims) {
    JwtBuilder builder =
        Jwts.builder()
            .header()
            .type(Header.JWT_TYPE)
            .and()
            .issuer(issuer)
            .subject(claims.subject())
            .audience()
            .add(claims.audience())
            .and()
            .id(claims.tokenId())
            .issuedAt(Date.from(claims.issueAt()))
            .expiration(Date.from(claims.expiredAt()));

    if (claims.tokenPurpose() == TokenPurpose.VERIFY) {
      builder
          .claim(TOKEN_PURPOSE, TokenPurpose.VERIFY.name())
          .claim(
              VERIFY_PURPOSE,
              claims.verifyPurpose() != null ? claims.verifyPurpose().name() : null);
    } else {
      builder
          .claim(TOKEN_PURPOSE, claims.tokenPurpose().name())
          .claim(ROLE, claims.role() != null ? claims.role().name() : null);
    }

    String raw = builder.signWith(secret).compact();

    return Token.builder().raw(raw).expiredAt(claims.expiredAt()).build();
  }

  @Override
  public TokenClaims verifyAndParseToken(String token, Date now) {
    try {
      JwtParser parser =
          Jwts.parser()
              .clock(() -> now)
              .clockSkewSeconds(skewSeconds)
              .requireIssuer(issuer)
              .verifyWith(secret)
              .build();

      Jws<Claims> jws = parser.parseSignedClaims(token);

      Claims claims = jws.getPayload();

      // 문자열로 역직렬화하여 Enum 복원
      String purposeStr = claims.get(TOKEN_PURPOSE, String.class);
      TokenPurpose purpose = purposeStr != null ? TokenPurpose.valueOf(purposeStr) : null;

      Role role =
          (purpose != null && purpose != TokenPurpose.VERIFY)
              ? toEnumOrNull(claims.get(ROLE, String.class), Role.class)
              : null;

      VerifyPurpose verifyPurpose =
          (purpose == TokenPurpose.VERIFY)
              ? toEnumOrNull(claims.get(VERIFY_PURPOSE, String.class), VerifyPurpose.class)
              : null;

      String audience = claims.getAudience().stream().findAny().orElse(null);

      return TokenClaims.builder()
          .issuer(claims.getIssuer())
          .subject(claims.getSubject())
          .role(role)
          .audience(audience)
          .expiredAt(claims.getExpiration().toInstant())
          .issueAt(claims.getIssuedAt().toInstant())
          .tokenId(claims.getId())
          .tokenPurpose(purpose)
          .verifyPurpose(verifyPurpose)
          .build();
    } catch (ExpiredJwtException e) {
      throw new TokenExpiredException();
    } catch (JwtException e) {
      throw new TokenParseException();
    }
  }

  private static <E extends Enum<E>> E toEnumOrNull(String name, Class<E> type) {
    if (name == null) return null;
    return Enum.valueOf(type, name);
  }
}
