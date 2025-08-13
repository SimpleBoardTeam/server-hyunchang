package com.simpleboard.board.permission.application.command;

import com.simpleboard.board.permission.application.command.dto.DelegateRoleCommand;
import com.simpleboard.board.permission.application.common.UserFetchService;
import com.simpleboard.board.permission.domain.entity.PermissionPolicy;
import com.simpleboard.board.permission.domain.repository.PermissionRepository;
import com.simpleboard.board.permission.domain.repository.RoleCatalog;
import com.simpleboard.board.permission.domain.vo.Role;
import com.simpleboard.board.permission.domain.vo.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * PermissionCommand 유스케이스.
 *
 * @domain application-service
 * @transactional
 */
@Service
@RequiredArgsConstructor
public class PermissionCommandService {
  private final PermissionRepository permissionRepository;
  private final UserFetchService userFetchService;
  private final RoleCatalog roleCatalog;

  public void delegateRole(Long boardId, DelegateRoleCommand delegateRoleCommand) {
    PermissionPolicy permissionPolicy = permissionRepository.getByBoardId(boardId);
    Long toMemberId = userFetchService.getUserIdByNickname(delegateRoleCommand.toUserNickname());
    Role role = roleCatalog.get(delegateRoleCommand.roleName());
    permissionPolicy.delegateRole(
        delegateRoleCommand.fromUserId(), toMemberId, role);
    permissionRepository.save(permissionPolicy);
  }

  public void deletePermissionPolicy(Long boardId) {
    PermissionPolicy permissionPolicy = permissionRepository.getByBoardId(boardId);
    permissionRepository.delete(permissionPolicy);
  }

  public void createPermissionPolicy(Long boardId, Long userID) {
    PermissionPolicy permissionPolicy = PermissionPolicy.create(boardId, userID, roleCatalog.get(
        RoleName.BOARD_ADMIN));
    permissionRepository.save(permissionPolicy);
  }
}
