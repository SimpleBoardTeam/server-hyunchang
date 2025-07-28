package com.simpleboard.board.permission.application.command;

import com.simpleboard.board.permission.application.command.dto.DelegateRoleCommand;
import com.simpleboard.board.permission.application.command.repository.PermissionCommandRepository;
import com.simpleboard.board.permission.application.common.UserFetchService;
import com.simpleboard.board.permission.domain.PermissionPolicy;
import org.springframework.stereotype.Service;

/**
 * PermissionCommand 유스케이스.
 *
 * @domain application-service
 * @transactional
 */
@Service
public class PermissionCommandService {
  private final PermissionCommandRepository permissionCommandRepository;
  private final UserFetchService userFetchService;

  public PermissionCommandService(
      PermissionCommandRepository permissionCommandRepository, UserFetchService userFetchService) {
    this.permissionCommandRepository = permissionCommandRepository;
    this.userFetchService = userFetchService;
  }

  public void delegateRole(Long boardId, DelegateRoleCommand delegateRoleCommand) {
    PermissionPolicy permissionPolicy = permissionCommandRepository.getByBoardId(boardId);
    Long toMemberId = userFetchService.getUserIdByNickname(delegateRoleCommand.toUserNickname());
    permissionPolicy.delegateRole(
        delegateRoleCommand.fromUserId(), toMemberId, delegateRoleCommand.roleName());
    permissionCommandRepository.save(permissionPolicy);
  }

  public void deletePermissionPolicy(Long boardId) {
    PermissionPolicy permissionPolicy = permissionCommandRepository.getByBoardId(boardId);
    permissionCommandRepository.delete(permissionPolicy);
  }

  public void createPermissionPolicy(Long boardId, Long userID) {
    PermissionPolicy permissionPolicy = PermissionPolicy.create(boardId, userID);
    permissionCommandRepository.save(permissionPolicy);
  }
}
