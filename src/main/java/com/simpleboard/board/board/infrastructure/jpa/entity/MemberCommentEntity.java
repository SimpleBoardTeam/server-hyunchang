package com.simpleboard.board.board.infrastructure.jpa.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("MEMBER_COMMENT")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberCommentEntity extends CommentEntity {

  private Long writerId;
}
