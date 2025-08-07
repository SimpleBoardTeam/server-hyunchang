package com.simpleboard.board.board.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HASHTAG")
@Getter
public class HashTagEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "HASHTAG_ID")
  private Long id;

  @Column(nullable = false, unique = true)
  private String tag;
}
