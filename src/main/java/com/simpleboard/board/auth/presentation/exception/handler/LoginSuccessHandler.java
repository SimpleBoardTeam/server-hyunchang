package com.simpleboard.board.auth.presentation.exception.handler;

import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.Cookie.REFRESH_COOKIE;
import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.Header.AUTHORIZATION;
import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.Header.BEARER;

import com.simpleboard.board.auth.application.service.AuthPrincipal;
import com.simpleboard.board.auth.application.service.AuthPrincipalService;
import com.simpleboard.board.auth.domain.token.vo.TokenPair;
import com.simpleboard.board.auth.presentation.util.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * <b>email 로그인 성공 핸들러</b>
 *
 * <p>UsernamePasswordAuthenticationToken을 통한 로그인 셍공시 실행
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final AuthPrincipalService authPrincipalService;
  private final CookieUtils cookieUtils;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();

    TokenPair pair = authPrincipalService.issueTokenPair(principal);

    response.addHeader(AUTHORIZATION, BEARER + pair.access().raw());
    response.addCookie(cookieUtils.buildCookie(pair.refresh(), REFRESH_COOKIE));
    response.setStatus(HttpServletResponse.SC_OK);
  }
}
