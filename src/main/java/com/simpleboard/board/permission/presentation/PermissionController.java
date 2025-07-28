package com.simpleboard.board.permission.presentation;

import com.simpleboard.board.permission.application.command.PermissionCommandService;
import com.simpleboard.board.permission.application.command.dto.DelegateRoleCommand;
import com.simpleboard.board.permission.presentation.dto.DelegateRoleForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/permissions")
public class PermissionController {
  private final PermissionCommandService permissionCommandService;

  @PostMapping("/boards/{boardId}/delegate")
  public ResponseEntity<Void> delegateRole(
      @PathVariable Long boardId, @RequestBody DelegateRoleForm form) {
    permissionCommandService.delegateRole(
        boardId,
        new DelegateRoleCommand(form.fromUserId(), form.toUserNickname(), form.roleType()));
    return ResponseEntity.ok().build();
  }
}
