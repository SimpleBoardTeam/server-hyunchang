package com.simpleboard.board.member.domain;

import java.time.LocalDateTime;
import lombok.Getter;

/**
 * <b>Member</b> Aggregate Root.
 *
 * <p>회원의 핵심 정보를 관리하는 Aggregate Root로, 닉네임, 성별, 출생 연도, 가입 일시 및 탈퇴 여부를 포함하여 회원 도메인의 주요 상태와 행위를 책임진다.
 *
 * <p>포함 엔티티 및 값 객체:
 *
 * <ul>
 *   <li>{@link Nickname}
 *   <li>{@link Gender}
 *   <li>{@link BirthYear}
 * </ul>
 *
 * @domain aggregate-root @See 연관 Service 이름
 * @since 1.0
 */
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

  public Member(
      Long memberId, Nickname nickname, String gender, String birthYear, LocalDateTime signUpDate) {
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
