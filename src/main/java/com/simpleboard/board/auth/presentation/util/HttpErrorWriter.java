package com.simpleboard.board.auth.presentation.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <b>ErrorCode에 맞는 Http response를 직접 작성하는 Utility 클래스</b>
 *
 * <p>Filter 단에서 발생하는 Exception들을 별도 처리하기 위해 샤용
 */
public class HttpErrorWriter {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private HttpErrorWriter() {}

  public static void writeError(HttpServletResponse response, ErrorCode errorCode)
      throws IOException {
    if (response.isCommitted()) return;
    response.setStatus(errorCode.getStatus().value());
    response.setContentType("application/json");
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    MAPPER.writeValue(response.getWriter(), ErrorResponse.of(errorCode));
  }
}
