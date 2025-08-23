package com.simpleboard.board.auth.presentation.exception.handler;

import com.simpleboard.board.auth.presentation.util.HttpErrorWriter;
import com.simpleboard.board.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * <b>email 로그인 실패 핸들러</b>
 *
 * <p>UsernamePasswordAuthenticationToken을 통한 로그인 실패시 실행
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException {
    HttpErrorWriter.writeError(response, ErrorCode.LOGIN_FAIL);
  }
}
