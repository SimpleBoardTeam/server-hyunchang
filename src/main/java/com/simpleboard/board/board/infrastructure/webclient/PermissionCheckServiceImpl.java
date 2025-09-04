package com.simpleboard.board.board.infrastructure.webclient;

import com.simpleboard.board.board.application.service.PermissionCheckService;
import com.simpleboard.board.board.infrastructure.webclient.dto.CanDeleteResponse;
import com.simpleboard.board.board.infrastructure.webclient.exception.BoardDeleteNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    CanDeleteResponse response = webClient.get()
        .uri(internalBaseUrl + "/internal/permissions/boards/{boardId}/delete?userId={userId}",
            boardId, memberId)
        .retrieve()
        .bodyToMono(CanDeleteResponse.class)
        .block();

    if (response == null || !response.canDelete()) {
      throw new BoardDeleteNotAllowedException();
    }
  }
}
