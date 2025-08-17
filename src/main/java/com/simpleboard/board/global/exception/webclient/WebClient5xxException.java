package com.simpleboard.board.global.exception.webclient;

import com.simpleboard.board.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * <b>Custom Exception Class <b>
 *
 * <p>WebClient 5xx 응답 처리용 커스텀 예외
 */
@Getter
public class WebClient5xxException extends RuntimeException {
  private final HttpStatus status;

  private static final String DEFAULT_MESSAGE = ErrorCode.WEBCLIENT_5XX_ERROR.getMessage();

  public WebClient5xxException(HttpStatus status, String message) {
    super(message != null && !message.isBlank() ? message : DEFAULT_MESSAGE);
    this.status = status;
  }
}
