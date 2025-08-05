package com.simpleboard.board.permission.application.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.simpleboard.board.permission.application.command.dto.DelegateRoleCommand;
import com.simpleboard.board.permission.domain.PermissionRepository;
import com.simpleboard.board.permission.application.common.UserFetchService;
import com.simpleboard.board.permission.domain.Permission;
import com.simpleboard.board.permission.domain.PermissionPolicy;
import com.simpleboard.board.permission.domain.RoleName;
import com.simpleboard.board.permission.domain.exception.AssignmentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PermissionCommandServiceTest {

  @Mock
  PermissionRepository permissionRepository;

  @Mock UserFetchService userFetchService;

  @InjectMocks PermissionCommandService permissionCommandService;

  PermissionPolicy permissionPolicy;
  Long boardId;
  Long creatorId;

  @BeforeEach
  void setUp() {
    boardId = 1L;
    creatorId = 1L;
    permissionPolicy = PermissionPolicy.create(boardId, creatorId);
  }

  @Test
  void delegateRole_성공() {
    // given
    String toNickname = "nickname";
    Long toUserId = 20L;
    RoleName roleName = RoleName.BOARD_ADMIN;
    DelegateRoleCommand command = new DelegateRoleCommand(creatorId, toNickname, roleName);
    when(permissionRepository.getByBoardId(boardId)).thenReturn(permissionPolicy);
    when(userFetchService.getUserIdByNickname(toNickname)).thenReturn(toUserId);

    // when
    permissionCommandService.delegateRole(boardId, command);

    // then
    assertThat(permissionPolicy.can(toUserId, Permission.CREATE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(toUserId, Permission.DELETE_BOARD)).isTrue();
    assertThat(permissionPolicy.can(creatorId, Permission.CREATE_BOARD)).isFalse();
    assertThat(permissionPolicy.can(creatorId, Permission.DELETE_BOARD)).isFalse();
  }

  @Test
  void delegateRole_실패_fromUser가_권한없음() {
    // give
    Long fromUserId = 10L;
    String toNickname = "nickname";
    Long toUserId = 20L;
    RoleName roleName = RoleName.BOARD_ADMIN;
    DelegateRoleCommand command = new DelegateRoleCommand(fromUserId, toNickname, roleName);
    when(permissionRepository.getByBoardId(boardId)).thenReturn(permissionPolicy);
    when(userFetchService.getUserIdByNickname(toNickname)).thenReturn(toUserId);

    // when & then
    assertThatThrownBy(() -> permissionCommandService.delegateRole(boardId, command))
        .isInstanceOf(AssignmentNotFoundException.class);
  }
}
