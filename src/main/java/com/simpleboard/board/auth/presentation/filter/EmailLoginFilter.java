package com.simpleboard.board.auth.presentation.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpleboard.board.auth.presentation.dto.request.EmailLoginForm;
import com.simpleboard.board.auth.presentation.exception.SecurityFilterException;
import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <b>Email 로그인을 처리하는 Filter</b>
 *
 * <p>실패시 AuthenticationException 발생
 *
 * <p>코어 계층에서 ServiceException 발생시 이를 AuthenticationException로 변환
 */
@RequiredArgsConstructor
public class EmailLoginFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    try {
      EmailLoginForm form = objectMapper.readValue(request.getInputStream(), EmailLoginForm.class);
      var authToken = new UsernamePasswordAuthenticationToken(form.email(), form.password());
      return authenticationManager.authenticate(authToken);
    } catch (IOException e) {
      throw new SecurityFilterException(ErrorCode.LOGIN_FAIL);
    } catch (ServiceException e) {
      throw new SecurityFilterException(e.getErrorCode());
    }
  }
}
