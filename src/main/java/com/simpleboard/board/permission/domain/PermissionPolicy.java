package com.simpleboard.board.permission.domain;

import com.simpleboard.board.permission.domain.exception.AssignmentNotFoundException;
import com.simpleboard.board.permission.domain.exception.RoleDelegationException;
import java.util.*;

public class PermissionPolicy {

  private Long boardId;

  private final List<ManagerAssignment> managerAssignments = new ArrayList<>();

  private PermissionPolicy(Long boardId) {
    this.boardId = boardId;
  }

  public static PermissionPolicy create(Long boardId, Long userId) {
    PermissionPolicy permissionPolicy = new PermissionPolicy(boardId);
    permissionPolicy.assignRole(userId, RoleName.BOARD_ADMIN);
    return permissionPolicy;
  }

  private ManagerAssignment findAssignment(Long userId) {
    return managerAssignments.stream()
        .filter(assignment -> assignment.isOwnedBy(userId))
        .findFirst()
        .orElseThrow(() -> new AssignmentNotFoundException(userId));
  }

  public void deleteAssignment(Long userId) {
    managerAssignments.removeIf(a -> a.isOwnedBy(userId));
  }

  public boolean can(Long userId, Permission permission) {
    return managerAssignments.stream()
        .filter(a -> a.isOwnedBy(userId))
        .findFirst()
        .map(a -> a.hasPermission(permission))
        .orElse(false);
  }

  public void assignRole(Long userId, RoleName roleName) {
    managerAssignments.add(ManagerAssignment.create(userId, roleName));
  }

  public void delegateRole(Long from, Long to, RoleName roleName) {
    ManagerAssignment fromAssignment = findAssignment(from);

    if (!fromAssignment.hasRole(roleName)) {
      throw new RoleDelegationException(from, roleName);
    }

    assignRole(to, roleName);
    deleteAssignment(from);
  }
}
