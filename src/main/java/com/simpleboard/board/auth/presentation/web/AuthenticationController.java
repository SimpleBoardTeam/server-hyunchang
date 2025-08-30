package com.simpleboard.board.auth.presentation.web;

import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.Cookie.REFRESH_COOKIE;
import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.Header.AUTHORIZATION;
import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.Header.BEARER;

import com.simpleboard.board.auth.application.service.TokenApplicationService;
import com.simpleboard.board.auth.application.service.UserAuthCommandService;
import com.simpleboard.board.auth.domain.token.exception.RefreshTokenInvalidException;
import com.simpleboard.board.auth.domain.token.vo.TokenPair;
import com.simpleboard.board.auth.presentation.util.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "인증", description = "토큰, 인증 관련 API")
public class AuthenticationController {

  private final CookieUtils cookieUtils;
  private final TokenApplicationService tokenApplicationService;
  private final UserAuthCommandService userAuthCommandService;

  @Operation(
      summary = "Access 토큰 재발급",
      description = "refresh token을 사용하여 access 토큰을 재발급, refresh 토큰은 rotation")
  @PostMapping("/token/rotate")
  public ResponseEntity<Void> reclaimAccessToken(
      HttpServletRequest request, HttpServletResponse response) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) throw new RefreshTokenInvalidException();
    String refreshTokenRaw = getCookieToken(cookies, REFRESH_COOKIE);
    TokenPair pair = tokenApplicationService.rotateRefreshToken(refreshTokenRaw);
    response.addHeader(AUTHORIZATION, BEARER + pair.access().raw());
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.addCookie(cookieUtils.buildCookie(pair.refresh(), REFRESH_COOKIE));
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "로그아웃", description = "refresh token 쿠키를 만료시키고 토큰을 블랙리스트에 등록")
  @PostMapping("/signout")
  public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equals(REFRESH_COOKIE))
          .findFirst()
          .ifPresent(
              refreeshCookie -> tokenApplicationService.enrollBlacklist(refreeshCookie.getValue()));
    }
    response.addCookie(cookieUtils.expire(REFRESH_COOKIE));
    return ResponseEntity.noContent().build();
  }

  private String getCookieToken(Cookie[] cookies, String cookieName) {
    String refreshTokenRaw =
        Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(cookieName))
            .findFirst()
            .orElseThrow(RefreshTokenInvalidException::new)
            .getValue();
    return refreshTokenRaw;
  }
}
