package com.simpleboard.board.global.exception.webclient;

import com.simpleboard.board.global.exception.ErrorCode;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest
@Import(WebClientErrorHandlerTest.TestConfig.class)
class WebClientErrorHandlerTest {

  static MockWebServer mockWebServer;
  private final String TEST_ERROR_MESSAGE = "TEST_MESSAGE";

  @BeforeAll
  static void setUp() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    mockWebServer.shutdown();
  }

  @Configuration
  static class TestConfig {
    @Bean
    public WebClient webClient() {
      return WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build();
    }

    @Bean
    public ErrorHandlerTestService errorHandlerTestService(WebClient webClient) {
      return new ErrorHandlerTestService(webClient);
    }
  }

  @Autowired ErrorHandlerTestService errorHandlerTestService;

  @Test
  @DisplayName("4xx 응답에 body가 있을 때 body 내용으로 WebClient4xxException 이 발생한다")
  void handle4xxWithBody_shouldThrowWebClient4xxException() {
    mockWebServer.enqueue(new MockResponse().setResponseCode(404).setBody(TEST_ERROR_MESSAGE));

    StepVerifier.create(errorHandlerTestService.callExternal())
        .expectErrorMatches(
            throwable ->
                throwable instanceof WebClient4xxException
                    && throwable.getMessage().equals(TEST_ERROR_MESSAGE))
        .verify();
  }

  @Test
  @DisplayName("4xx 응답에 body가 없을 때 기본 메시지로 WebClient4xxException 이 발생한다")
  void handle4xxWithoutBody_shouldThrowWebClient4xxExceptionWithDefaultMessage() {
    mockWebServer.enqueue(new MockResponse().setResponseCode(404));

    StepVerifier.create(errorHandlerTestService.callExternal())
        .expectErrorMatches(
            throwable ->
                throwable instanceof WebClient4xxException
                    && throwable.getMessage().equals(ErrorCode.WEBCLIENT_4XX_ERROR.getMessage()))
        .verify();
  }

  @Test
  @DisplayName("5xx 응답에 body가 있을 때 body 내용으로 WebClient5xxException 이 발생한다")
  void handle5xxWithBody_shouldThrowWebClient5xxException() {
    mockWebServer.enqueue(new MockResponse().setResponseCode(500).setBody(TEST_ERROR_MESSAGE));

    StepVerifier.create(errorHandlerTestService.callExternal())
        .expectErrorMatches(
            throwable ->
                throwable instanceof WebClient5xxException
                    && throwable.getMessage().equals(TEST_ERROR_MESSAGE))
        .verify();
  }

  @Test
  @DisplayName("5xx 응답에 body가 없을 때 기본 메시지로 WebClient5xxException 이 발생한다")
  void handle5xxWithoutBody_shouldThrowWebClient5xxExceptionWithDefaultMessage() {
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));

    StepVerifier.create(errorHandlerTestService.callExternal())
        .expectErrorMatches(
            throwable ->
                throwable instanceof WebClient5xxException
                    && throwable.getMessage().equals(ErrorCode.WEBCLIENT_5XX_ERROR.getMessage()))
        .verify();
  }
}
