package com.simpleboard.board.permission.application.command;

import com.simpleboard.board.permission.application.command.dto.DelegateRoleCommand;
import com.simpleboard.board.permission.application.common.UserFetchService;
import com.simpleboard.board.permission.domain.PermissionPolicy;
import com.simpleboard.board.permission.domain.PermissionRepository;
import org.springframework.stereotype.Service;

/**
 * PermissionCommand 유스케이스.
 *
 * @domain application-service
 * @transactional
 */
@Service
public class PermissionCommandService {
  private final PermissionRepository permissionRepository;
  private final UserFetchService userFetchService;

  public PermissionCommandService(
      PermissionRepository permissionRepository, UserFetchService userFetchService) {
    this.permissionRepository = permissionRepository;
    this.userFetchService = userFetchService;
  }

  public void delegateRole(Long boardId, DelegateRoleCommand delegateRoleCommand) {
    PermissionPolicy permissionPolicy = permissionRepository.getByBoardId(boardId);
    Long toMemberId = userFetchService.getUserIdByNickname(delegateRoleCommand.toUserNickname());
    permissionPolicy.delegateRole(
        delegateRoleCommand.fromUserId(), toMemberId, delegateRoleCommand.roleName());
    permissionRepository.save(permissionPolicy);
  }

  public void deletePermissionPolicy(Long boardId) {
    PermissionPolicy permissionPolicy = permissionRepository.getByBoardId(boardId);
    permissionRepository.delete(permissionPolicy);
  }

  public void createPermissionPolicy(Long boardId, Long userID) {
    PermissionPolicy permissionPolicy = PermissionPolicy.create(boardId, userID);
    permissionRepository.save(permissionPolicy);
  }
}
