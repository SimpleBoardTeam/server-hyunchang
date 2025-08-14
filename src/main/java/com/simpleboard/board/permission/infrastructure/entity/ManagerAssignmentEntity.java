package com.simpleboard.board.permission.infrastructure.entity;

import com.simpleboard.board.permission.domain.entity.ManagerAssignment;
import com.simpleboard.board.permission.domain.vo.Permission;
import com.simpleboard.board.permission.domain.vo.Role;
import com.simpleboard.board.permission.domain.vo.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permis_manager_assignment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@IdClass(ManagerAssignmentId.class)
public class ManagerAssignmentEntity {

  @Id
  @Column(name = "board_id")
  private Long boardId;

  @Id private Long userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "role_name")
  private RoleName roleName;
}
