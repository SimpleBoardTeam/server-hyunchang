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
 * @domain aggregate-root
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

  private Member(
      Long memberId,
      Nickname nickname,
      Gender gender,
      BirthYear birthYear,
      LocalDateTime signUpDate) {
    this.memberId = memberId;
    this.nickname = nickname;
    this.gender = gender;
    this.birthYear = birthYear;
    this.signUpDate = signUpDate;
    this.isWithdrawn = false;
  }

  /** signUpDate를 명시적으로 넣는 경우 */
  public static Member create(
      Long memberId,
      Nickname nickname,
      Gender gender,
      BirthYear birthYear,
      LocalDateTime signUpDate,
      NicknamePolicy nicknamePolicy) {

    nicknamePolicy.ensureUnique(nickname);
    return new Member(memberId, nickname, gender, birthYear, signUpDate);
  }

  /** signUpDate를 지정하지 않으면 now()를 기본으로 설정 */
  public static Member create(
      Long memberId,
      Nickname nickname,
      Gender gender,
      BirthYear birthYear,
      NicknamePolicy nicknamePolicy) {

    return create(memberId, nickname, gender, birthYear, LocalDateTime.now(), nicknamePolicy);
  }

  public void withdraw() {
    this.isWithdrawn = true;
  }

  public void updateNickname(Nickname newNickname, NicknamePolicy nicknamePolicy) {
    nicknamePolicy.ensureUnique(newNickname);
    this.nickname = newNickname;
  }
}
