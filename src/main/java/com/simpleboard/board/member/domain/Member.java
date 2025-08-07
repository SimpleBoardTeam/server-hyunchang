package com.simpleboard.board.member.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Member {

  private final Long memberId;
  private Nickname nickname;
  private final Gender gender;
  private final BirthYear birthYear;
  private final LocalDateTime signUpDate;
  private boolean isWithdrawn;

  public Member(Long memberId, Nickname nickname, String gender, String birthYear) {
    this(memberId, nickname, gender, birthYear, LocalDateTime.now());
  }

  public Member(Long memberId, Nickname nickname, String gender, String birthYear, LocalDateTime signUpDate) {
    this.memberId = memberId;
    this.nickname = nickname;
    this.gender = Gender.valueOf(gender);
    this.birthYear = BirthYear.of(birthYear);
    this.signUpDate = signUpDate;
    this.isWithdrawn = false;
  }

  public void withdraw() {
    this.isWithdrawn = true;
  }

  public void updateNickname(String rawNickname) {
    this.nickname = Nickname.of(rawNickname);
  }
}