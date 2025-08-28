package com.simpleboard.board.auth.presentation.util;

import java.util.List;

/**
 * <b>Constant String 제공 클래스</b>
 *
 * <p>Auth B.C에서 반복적으로 사용되는 Constant string들을 관리, 제공
 */
public class AuthStringProvider {

  public static class Cookie {
    public static final String REFRESH_COOKIE = "Authorization-Refresh";
  }

  public static class Token{
    public static final String TOKEN_PURPOSE = "tokenPurpose";
    public static final String VERIFY_PURPOSE = "verifyPurpose";
    public static final String ROLE = "role";
  }

  public static class Filter {
    public static final String ROLE_GUEST = "ROLE_GUEST";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String FINGERPRINT = "fingerprint";
  }

  public static class Header {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
  }

  public static final List<String> WHITELIST =
      List.of(
          "/auth/login/email",
          "/auth/refresh",
          "/auth/logout",
          "/v3/api-docs",
          "/swagger-ui",
          "/swagger-ui.html",
          "/healthCheck");
}
