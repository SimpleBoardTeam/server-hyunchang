package com.simpleboard.board.board.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "POST_LIKE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostLikeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "POSTLIKE_ID")
  private Long id;

  @Column(nullable = false)
  private String vid;

  private Long likedMemberId;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "POST_ID")
  private PostEntity post;
}
