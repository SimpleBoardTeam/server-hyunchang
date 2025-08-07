package com.simpleboard.board.permission.infrastructure.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ManagerAssignmentId implements Serializable {
  private Long boardId;
  private Long userId;
}
