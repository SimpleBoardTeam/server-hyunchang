package com.simpleboard.board.auth.presentation.filter;

import com.simpleboard.board.auth.presentation.util.AuthStringProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class FingerprintFilter extends OncePerRequestFilter {

  /**
   * <b>클라이언트의 fingerprint 생성 필터</b>
   *
   * <p>ip + user-agent 조합으로 fingerprint 생성 TODO: 향후 고도화 필요
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String fingerprint = getClientIpAddress(request) + "_" + request.getHeader("User-Agent");

    Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
    if (details != null && !(details instanceof Map)) {
      filterChain.doFilter(request, response);
      return;
    }
    if (details == null) {
      details = new HashMap<String, String>();
      ((AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication())
          .setDetails(details);
    }
    ((Map<String, String>) details).put(AuthStringProvider.Filter.FINGERPRINT, fingerprint);
    filterChain.doFilter(request, response);
  }

  private String getClientIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");

    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("Proxy-Client-IP");
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("WL-Proxy-Client-IP");
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("HTTP_CLIENT_IP");
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) ip = request.getRemoteAddr();

    return ip;
  }
}
