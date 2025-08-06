package com.simpleboard.board.permission.application.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.simpleboard.board.permission.application.command.dto.DelegateRoleCommand;
import com.simpleboard.board.permission.application.common.UserFetchService;
import com.simpleboard.board.permission.domain.Permission;
import com.simpleboard.board.permission.domain.PermissionPolicy;
import com.simpleboard.board.permission.domain.RoleName;
import com.simpleboard.board.permission.domain.exception.AssignmentNotFoundException;
import com.simpleboard.board.permission.infrastructure.entity.PermissionPolicyEntity;
import com.simpleboard.board.permission.infrastructure.repository.command.PermissionCommandJpaRepository;
import com.simpleboard.board.permission.infrastructure.repository.command.PermissionRepositoryImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PermissionCommandServiceTest {

  PermissionCommandService permissionCommandService;

  @Mock PermissionCommandJpaRepository permissionCommandJpaRepository;

  @Mock UserFetchService userFetchService;

  PermissionPolicy permissionPolicy;
  Long boardId;
  Long creatorId;

  @BeforeEach
  void setUp() {
    boardId = 1L;
    creatorId = 1L;
    permissionPolicy = PermissionPolicy.create(boardId, creatorId);

    permissionCommandService =
        new PermissionCommandService(
            new PermissionRepositoryImpl(permissionCommandJpaRepository), userFetchService);
  }

  @Test
  void delegateRole_성공() {
    // given
    String toNickname = "nickname";
    Long toUserId = 20L;
    RoleName roleName = RoleName.BOARD_ADMIN;
    DelegateRoleCommand command = new DelegateRoleCommand(creatorId, toNickname, roleName);

    when(userFetchService.getUserIdByNickname(toNickname)).thenReturn(toUserId);
    when(permissionCommandJpaRepository.findByBoardId(boardId))
        .thenReturn(Optional.of(PermissionPolicyEntity.from(permissionPolicy)));
    when(permissionCommandJpaRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // when
    permissionCommandService.delegateRole(boardId, command);

    // then
    ArgumentCaptor<PermissionPolicyEntity> captor =
        ArgumentCaptor.forClass(PermissionPolicyEntity.class);
    verify(permissionCommandJpaRepository).save(captor.capture());
    PermissionPolicy updatedPolicy = captor.getValue().toDomain();

    assertThat(updatedPolicy.can(toUserId, Permission.CREATE_BOARD)).isTrue();
    assertThat(updatedPolicy.can(toUserId, Permission.DELETE_BOARD)).isTrue();
    assertThat(updatedPolicy.can(creatorId, Permission.CREATE_BOARD)).isFalse();
    assertThat(updatedPolicy.can(creatorId, Permission.DELETE_BOARD)).isFalse();
  }

  @Test
  void delegateRole_실패_fromUser가_권한없음() {
    // given
    Long fromUserId = 10L; // 권한이 없는 사용자
    String toNickname = "nickname";
    Long toUserId = 20L;
    RoleName roleName = RoleName.BOARD_ADMIN;
    DelegateRoleCommand command = new DelegateRoleCommand(fromUserId, toNickname, roleName);

    // Board에 fromUserId만 존재하고, 해당 역할이 없도록 구성된 PermissionPolicy
    PermissionPolicy onlyCreatorPolicy =
        PermissionPolicy.create(boardId, creatorId); // creatorId만 있음

    when(userFetchService.getUserIdByNickname(toNickname)).thenReturn(toUserId);
    when(permissionCommandJpaRepository.findByBoardId(boardId))
        .thenReturn(Optional.of(PermissionPolicyEntity.from(onlyCreatorPolicy)));

    // when & then
    assertThatThrownBy(() -> permissionCommandService.delegateRole(boardId, command))
        .isInstanceOf(AssignmentNotFoundException.class);
  }
}
