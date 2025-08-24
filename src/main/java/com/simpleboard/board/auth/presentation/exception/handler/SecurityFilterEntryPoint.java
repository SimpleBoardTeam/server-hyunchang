package com.simpleboard.board.auth.presentation.exception.handler;

import com.simpleboard.board.auth.presentation.exception.SecurityFilterException;
import com.simpleboard.board.auth.presentation.util.HttpErrorWriter;
import com.simpleboard.board.global.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class SecurityFilterEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    ErrorCode errorCode =
        (exception instanceof SecurityFilterException)
            ? ((SecurityFilterException) exception).getErrorCode()
            : ErrorCode.LOGIN_FAIL;

    HttpErrorWriter.writeError(response, errorCode);
  }
}
