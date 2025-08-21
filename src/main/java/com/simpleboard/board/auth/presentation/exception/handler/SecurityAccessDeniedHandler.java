package com.simpleboard.board.auth.presentation.exception.handler;

import com.simpleboard.board.auth.presentation.exception.SecurityAccessDeniedException;
import com.simpleboard.board.auth.presentation.util.HttpErrorWriter;
import com.simpleboard.board.global.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    ErrorCode errorCode =
        (accessDeniedException
                instanceof SecurityAccessDeniedException securityAccessDeniedException)
            ? securityAccessDeniedException.getErrorCode()
            : ErrorCode.FORBIDDEN;

    HttpErrorWriter.writeError(response, errorCode);
  }
}
