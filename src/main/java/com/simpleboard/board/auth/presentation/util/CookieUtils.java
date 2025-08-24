package com.simpleboard.board.auth.presentation.util;

import com.simpleboard.board.auth.domain.token.util.ClockManager;
import com.simpleboard.board.auth.domain.token.vo.Token;
import jakarta.servlet.http.Cookie;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** <b>Http Response에 Cookie를 설정하는 Utility Class</b> */
@Component
@RequiredArgsConstructor
public class CookieUtils {

  public final ClockManager clockManager;

  public Cookie buildCookie(Token token, String cookieName) {
    int maxAge = (int) Duration.between(clockManager.now(), token.expiredAt()).getSeconds();
    return build(cookieName, token.raw(), maxAge, true);
  }

  public Cookie expire(String name) {
    return build(name, "", 0, true);
  }

  private Cookie build(String name, String value, int maxAge, boolean httpOnly) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setMaxAge(maxAge);
    cookie.setHttpOnly(httpOnly);
    return cookie;
  }
}
