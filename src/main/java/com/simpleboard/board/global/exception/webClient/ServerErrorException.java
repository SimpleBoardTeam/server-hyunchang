package com.simpleboard.board.global.exception.webClient;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * <b>Custom Exception Class <b>
 *
 * <p>WebClient 5xx 응답 처리용 커스텀 예외
 */
@Getter
public class ServerErrorException extends RuntimeException {
  private final HttpStatus status;

  public ServerErrorException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }
}