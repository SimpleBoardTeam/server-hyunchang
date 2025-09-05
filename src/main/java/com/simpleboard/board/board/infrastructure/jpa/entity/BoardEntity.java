package com.simpleboard.board.board.infrastructure.jpa.entity;

import com.simpleboard.board.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOARDS")
@Getter
@NoArgsConstructor
public class BoardEntity extends BaseEntity {

  public BoardEntity(Long boardId, String boardName, String normalizedName, Long managerId) {
    this.boardId = boardId;
    this.boardName = boardName;
    this.normalizedName = normalizedName;
    this.managerId = managerId;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_id")
  private Long boardId;

  @Column(name = "board_name", nullable = false, unique = true, length = 100)
  private String boardName;

  @Column(name = "normalized_name", nullable = false, unique = true, length = 100)
  private String normalizedName;

  @Column(name = "manager_id", nullable = false)
  private Long managerId;
}
