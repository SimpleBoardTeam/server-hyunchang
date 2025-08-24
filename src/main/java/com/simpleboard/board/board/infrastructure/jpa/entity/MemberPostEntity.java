package com.simpleboard.board.board.infrastructure.jpa.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@DiscriminatorValue("MEMBER_POST")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPostEntity extends PostEntity {

  private Long memberId;
}
