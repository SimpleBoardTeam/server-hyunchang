package com.simpleboard.board.permission.presentation;

import com.simpleboard.board.permission.application.command.PermissionCommandService;
import com.simpleboard.board.permission.presentation.converter.DelegateRoleConverter;
import com.simpleboard.board.permission.presentation.dto.DelegateRoleForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h2>PermissionController</h2>
 *
 * <p>게시판 권한 위임 관련 API를 제공합니다.</p>
 *
 * <table>
 *   <caption>API 목록</caption>
 *   <tr>
 *     <td>POST</td>
 *     <td>/permissions/boards/{boardId}/delegate</td>
 *     <td>특정 게시판에서 권한을 다른 사용자에게 위임</td>
 *   </tr>
 * </table>
 *
 * <p><b>권한</b> : 권한 위임</p>
 *
 * @domain adapter-in
 */
@Tag(name = "Permission API", description = "게시판 권한 관리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/permissions")
public class PermissionController {

  private final PermissionCommandService permissionCommandService;

  @Operation(
      summary = "게시판 권한 위임",
      description = "특정 게시판의 권한을 다른 사용자에게 위임합니다."
  )
  @PostMapping("/boards/{boardId}/delegate")
  public ResponseEntity<Void> delegateRole(
      @Parameter(description = "게시판 ID", required = true) @PathVariable Long boardId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "권한 위임 정보",
          required = true
      )
      @RequestBody DelegateRoleForm form
  ) {
    permissionCommandService.delegateRole(
        boardId,
        DelegateRoleConverter.toCommand(form)
    );
    return ResponseEntity.ok().build();
  }
}
