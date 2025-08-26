package com.simpleboard.board.board.infrastructure;

import com.simpleboard.board.board.application.service.PermissionCheckService;
import com.simpleboard.board.global.exception.webclient.WebClientErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class PermissionCheckServiceImpl implements PermissionCheckService {

  private final WebClient webClient;

  @Value("${internal.api.base-url:http://localhost:8080}")
  private String internalBaseUrl;

  @Override
  public void checkBoardCreatePermission(Long memberId) {
  }

  @Override
  public void checkBoardDeletePermission(Long memberId, Long boardId) {
    webClient
        .get()
        .uri(
            internalBaseUrl + "/internal/permissions/boards/{boardId}/delete?userId={userId}",
            boardId,
            memberId)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, WebClientErrorHandler::handle4xx)
        .onStatus(HttpStatusCode::is5xxServerError, WebClientErrorHandler::handle5xx)
        .toBodilessEntity()
        .block();
  }
}
