package com.simpleboard.board.permission.domain;

import com.simpleboard.board.permission.domain.exception.AssignmentNotFoundException;
import com.simpleboard.board.permission.domain.exception.RoleDelegationException;
import java.util.*;
import lombok.Builder;
import lombok.Getter;

/**
 * <b>PermissionPolicy</b> Aggregate Root.
 *
 * <p>멤버를 역할을 부여해 관리하고 권한 단위로 체크하는 도메인</p>
 * <p>
 * 포함 엔티티
 * <ul>
 *   <li>ManagerAssignment</li>
 * </ul>
 *
 * @domain aggregate-root
 * @since 1.0
 */
@Getter
@Builder
public class PermissionPolicy {

  private Long boardId;

  private final List<ManagerAssignment> managerAssignments;

  private PermissionPolicy(Long boardId, List<ManagerAssignment> managerAssignments) {
    this.boardId = boardId;
    this.managerAssignments = new ArrayList<>(managerAssignments);
  }

  public static PermissionPolicy create(Long boardId, Long userId) {
    PermissionPolicy permissionPolicy = new PermissionPolicy(boardId, new ArrayList<>());
    permissionPolicy.assignRole(userId, RoleName.BOARD_ADMIN);
    return permissionPolicy;
  }

  public static PermissionPolicy of(Long boardId, List<ManagerAssignment> assignments) {
    return new PermissionPolicy(boardId, assignments);
  }

  public List<ManagerAssignment> getManagerAssignments() {
    return Collections.unmodifiableList(managerAssignments);
  }

  public void deleteAssignment(Long userId) {
    managerAssignments.remove(findAssignmentByUserId(userId));
  }

  public boolean can(Long userId, Permission permission) {
    return managerAssignments.stream()
        .filter(a -> a.isOwnedBy(userId))
        .findFirst()
        .map(a -> a.hasPermission(permission))
        .orElse(false);
  }

  public void assignRole(Long userId, RoleName roleName) {
    managerAssignments.add(ManagerAssignment.create(this.boardId, userId, roleName));
  }

  public void delegateRole(Long from, Long to, RoleName roleName) {
    ManagerAssignment fromAssignment = findAssignmentByUserId(from);

    if (!fromAssignment.hasRole(roleName)) {
      throw new RoleDelegationException(from, roleName);
    }

    assignRole(to, roleName);
    deleteAssignment(from);
  }

  private ManagerAssignment findAssignmentByUserId(Long userId) {
    return managerAssignments.stream()
        .filter(a -> a.isOwnedBy(userId))
        .findFirst()
        .orElseThrow(() -> new AssignmentNotFoundException(userId));
  }
}
