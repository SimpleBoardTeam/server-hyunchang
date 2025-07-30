package com.simpleboard.board.board.infrastructure.jpa.entity;

import com.simpleboard.board.board.infrastructure.jpa.entity.embed.BoardEmbed;
import com.simpleboard.board.board.infrastructure.jpa.entity.embed.LikesEmbed;
import com.simpleboard.board.board.infrastructure.jpa.entity.embed.TagsEmbed;
import com.simpleboard.board.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "POST_TYPE")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "POST")
@SuperBuilder
@Getter
public abstract class PostEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "POST_ID")
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Long viewCount;

  @Column(nullable = false)
  private Boolean isDeleted;

  @Embedded private BoardEmbed board;

  @Embedded private TagsEmbed tags;

  @Embedded private LikesEmbed likes;
}
