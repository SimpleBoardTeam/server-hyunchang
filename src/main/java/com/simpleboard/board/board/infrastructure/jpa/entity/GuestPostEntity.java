package com.simpleboard.board.board.infrastructure.jpa.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("GUEST_POST")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GuestPostEntity extends PostEntity {

  private String nickname;

  private String password;
}
