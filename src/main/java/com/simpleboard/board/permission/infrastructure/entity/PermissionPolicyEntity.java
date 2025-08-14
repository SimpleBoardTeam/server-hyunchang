package com.simpleboard.board.permission.infrastructure.entity;

import com.simpleboard.board.permission.domain.entity.ManagerAssignment;
import com.simpleboard.board.permission.domain.entity.PermissionPolicy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permission_policy")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PermissionPolicyEntity {

  @Id
  @Column(name = "board_id")
  private Long boardId;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "board_id")
  private List<ManagerAssignmentEntity> managerAssignments = new ArrayList<>();

}
