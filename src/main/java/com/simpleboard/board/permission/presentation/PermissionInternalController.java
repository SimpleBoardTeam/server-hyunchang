package com.simpleboard.board.permission.presentation;

import com.simpleboard.board.permission.application.query.PermissionQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Permission Internal API", description = "게시판 권한 관련 내부 API")
@RestController
@RequestMapping("/internal/permissions")
@RequiredArgsConstructor
public class PermissionInternalController {

  private final PermissionQueryService permissionQueryService;

  @Operation(
      summary = "게시판 삭제 권한 확인",
      description = "해당 사용자가 지정한 게시판을 삭제할 권한이 있는지 확인합니다."
  )
  @GetMapping("/boards/{boardId}/delete")
  public ResponseEntity<CanDeleteResponse> checkBoardDeletePermission(
      @Parameter(description = "게시판 ID", required = true) @PathVariable Long boardId,
      @Parameter(description = "사용자 ID", required = true) @RequestParam Long userId
  ) {
    boolean canDelete = permissionQueryService.checkBoardDeletePermission(boardId, userId);
    return ResponseEntity.ok(new CanDeleteResponse(canDelete));
  }

  public record CanDeleteResponse(boolean canDelete) {}
}
