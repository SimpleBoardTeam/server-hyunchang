package com.simpleboard.board.global.exception.webclient;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ErrorHandlerTestService {

  private final WebClient webClient;

  public ErrorHandlerTestService(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<String> callExternal() {
    return webClient.get()
        .uri("/error-test")
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, WebClientErrorHandler::handle4xx)
        .onStatus(HttpStatusCode::is5xxServerError, WebClientErrorHandler::handle5xx)
        .bodyToMono(String.class);
  }
}
