package com.simpleboard.board.auth.presentation.filter;

import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.Filter.ANONYMOUS_USER;
import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.Filter.ROLE_GUEST;
import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.Header.BEARER;
import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.WHITELIST;

import com.simpleboard.board.auth.application.service.AuthPrincipalService;
import com.simpleboard.board.auth.presentation.exception.SecurityFilterException;
import com.simpleboard.board.auth.presentation.util.AuthStringProvider;
import com.simpleboard.board.global.exception.ServiceException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <b>로그인 토큰 파싱 필터</b>
 *
 * <p>로그인 토큰을 파싱 후 Authentication 설정
 *
 * <p>비로그인 유저의 경우도 AnonymousAuthentication 설정
 */
@RequiredArgsConstructor
public class TokenParsingFilter extends OncePerRequestFilter {

  private final AuthPrincipalService authPrincipalService;

  @Value("${security.anonymous-key}")
  private String anonymousKey;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    return WHITELIST.stream().anyMatch(path::startsWith);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = request.getHeader(AuthStringProvider.Header.AUTHORIZATION);

    if (token == null || !token.startsWith(BEARER)) {
      // 비 로그인 사용자(Guest)
      Authentication authentication =
          new AnonymousAuthenticationToken(
              anonymousKey, ANONYMOUS_USER, List.of(new SimpleGrantedAuthority(ROLE_GUEST)));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      // 로그인 사용자(Member)
      String raw = token.split(" ")[1];
      try {
        UserDetails userDetails = authPrincipalService.loadUserByTokenRaw(raw);
        Authentication auth =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
      } catch (ServiceException e) {
        throw new SecurityFilterException(e.getErrorCode());
      }
    }
    ((AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication())
        .setDetails(new HashMap<String, String>());
    filterChain.doFilter(request, response);
  }
}
