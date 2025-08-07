package com.simpleboard.board.permission.presentation;

import com.simpleboard.board.permission.application.command.PermissionCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test/permissions")
public class PermissionFakeController {
  private final PermissionCommandService permissionCommandService;

  @GetMapping("/boards/{boardId}/create")
  public ResponseEntity<Void> createPermissionPolicy(
      @PathVariable Long boardId, @RequestParam Long userId) {
    permissionCommandService.createPermissionPolicy(boardId, userId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/boards/{boardId}/delete")
  public ResponseEntity<Void> deletePermissionPolicy(
      @PathVariable Long boardId, @RequestParam Long userId) {
    permissionCommandService.deletePermissionPolicy(boardId);
    return ResponseEntity.ok().build();
  }
}
