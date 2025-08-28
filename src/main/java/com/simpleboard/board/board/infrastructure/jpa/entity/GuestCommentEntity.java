package com.simpleboard.board.board.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("GUEST_COMMENT")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GuestCommentEntity extends CommentEntity {

    private String nickname;

    private String password;
}
