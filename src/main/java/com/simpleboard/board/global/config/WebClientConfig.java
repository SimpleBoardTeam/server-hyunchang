package com.simpleboard.board.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class WebClientConfig {

  @Bean
  public WebClient webClient() {
    return WebClient.builder().filter(logRequest()).filter(logResponse()).build();
  }

  private ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(
        clientRequest -> {
          log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
          clientRequest
              .headers()
              .forEach(
                  (name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
          return Mono.just(clientRequest);
        });
  }

  private ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(
        clientResponse -> {
          clientResponse
              .headers()
              .asHttpHeaders()
              .forEach(
                  (name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
          return Mono.just(clientResponse);
        });
  }
}
