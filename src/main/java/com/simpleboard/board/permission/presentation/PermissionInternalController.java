package com.simpleboard.board.permission.presentation;

import com.simpleboard.board.permission.application.query.PermissionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/permissions")
@RequiredArgsConstructor
public class PermissionInternalController {

  private final PermissionQueryService permissionQueryService;

  @GetMapping("/boards/{boardId}/delete")
  public ResponseEntity<CanDeleteResponse> checkBoardDeletePermission(
      @PathVariable Long boardId, @RequestParam Long userId) {
    boolean canDelete = permissionQueryService.checkBoardDeletePermission(boardId, userId);
    return ResponseEntity.ok(new CanDeleteResponse(canDelete));
  }

  public record CanDeleteResponse(boolean canDelete) {}
}
