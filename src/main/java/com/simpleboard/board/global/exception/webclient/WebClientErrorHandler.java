package com.simpleboard.board.global.exception.webclient;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

/**
 * <b>WebClientErrorHandler </b>
 *
 * <p>WebClient의 onStatus()에서 HTTP 4xx/5xx 응답을 처리하고,
 * 커스텀 예외로 변환해주는 공통 유틸 클래스입니다.
 *
 * <p>ClientResponse로부터 상태 코드와 응답 본문을 추출하여,
 * 적절한 예외 로그를 기록하고 RuntimeException 하위 커스텀 예외를 발생시킵니다.
 */
public class WebClientErrorHandler {

  public static Mono<? extends Throwable> handle4xx(ClientResponse response) {
    return response.bodyToMono(String.class)
        .defaultIfEmpty("")
        .flatMap(body -> {
          int code = response.statusCode().value();
          HttpStatus status = HttpStatus.resolve(code);

          if (status == null) {
            return Mono.error(new RuntimeException("Unknown 4xx status code: " + code));
          }

          return Mono.error(new ClientErrorException(status, body));
        });
  }

  public static Mono<? extends Throwable> handle5xx(ClientResponse response) {
    return response.bodyToMono(String.class)
        .defaultIfEmpty("")
        .flatMap(body -> {
          int code = response.statusCode().value();
          HttpStatus status = HttpStatus.resolve(code);

          if (status == null) {
            return Mono.error(new RuntimeException("Unknown 5xx status code: " + code));
          }

          return Mono.error(new ServerErrorException(status, body));
        });
  }
}