package com.simpleboard.board.permission.presentation;

import com.simpleboard.board.permission.application.query.PermissionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <h2>PermissionInternalController</h2>
 *
 * <p>게시판 삭제 권한 확인을 위한 내부 API를 제공합니다.</p>
 *
 * <table>
 *   <caption>API 목록</caption>
 *   <tr>
 *     <td>GET</td>
 *     <td>/internal/permissions/boards/{boardId}/delete?userId=</td>
 *     <td>해당 사용자가 게시판 삭제 권한을 가졌는지 확인</td>
 *   </tr>
 * </table>
 *
 * <p><b>권한</b> : 삭제 권한 조회</p>
 *
 * @domain adapter-in
 */
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
