package com.simpleboard.board.permission.presentation;

import com.simpleboard.board.permission.application.command.PermissionCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h2>PermissionFakeController (테스트용 권한 컨트롤러)</h2>
 *
 * <p>테스트 목적의 임시 API를 제공합니다.</p>
 *
 * <table>
 *   <caption>API 목록</caption>
 *   <tr>
 *     <td>GET</td>
 *     <td>/test/permissions/boards/{boardId}/create?userId=</td>
 *     <td>지정한 boardId와 userId로 권한 정책 생성</td>
 *   </tr>
 *   <tr>
 *     <td>GET</td>
 *     <td>/test/permissions/boards/{boardId}/delete?userId=</td>
 *     <td>지정한 boardId에 대한 권한 정책 삭제</td>
 *   </tr>
 * </table>
 *
 * <p><b>권한</b> : 테스트 권한 생성/삭제</p>
 *
 * @domain adapter-in
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/test/permissions")
@Profile("!prod")
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
