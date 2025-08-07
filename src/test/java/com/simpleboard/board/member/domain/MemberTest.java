package com.simpleboard.board.member.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

  @Test
  @DisplayName("정상적으로 Member를 생성할 수 있다")
  void createMember() {
    // given
    Long memberId = 1L;
    Nickname nickname = Nickname.of("elton");
    String gender = "MALE";
    String birthYear = "1995";

    // when
    Member member = new Member(memberId, nickname, gender, birthYear);

    // then
    assertThat(member.getMemberId()).isEqualTo(memberId);
    assertThat(member.getNickname()).isEqualTo(nickname);
    assertThat(member.getGender()).isEqualTo(Gender.MALE);
    assertThat(member.getBirthYear().getValue()).isEqualTo(1995);
    assertThat(member.isWithdrawn()).isFalse();
  }

  @Test
  @DisplayName("Member 생성 시 signUpDate가 자동으로 설정된다")
  void signUpDateIsSetAutomatically() {
    // given
    Member member = new Member(1L, Nickname.of("elton"), "FEMALE", "1990");

    // then
    assertThat(member.getSignUpDate()).isNotNull();
    assertThat(member.getSignUpDate()).isBeforeOrEqualTo(LocalDateTime.now());
  }

  @Test
  @DisplayName("Member 생성 시 signUpDate를 명시적으로 주입할 수 있다")
  void createWithCustomSignUpDate() {
    // given
    LocalDateTime customDate = LocalDateTime.of(2020, 1, 1, 0, 0);
    Member member = new Member(1L, Nickname.of("elton"), "MALE", "1991", customDate);

    // then
    assertThat(member.getSignUpDate()).isEqualTo(customDate);
  }

  @Test
  @DisplayName("Member는 withdraw() 호출 시 탈퇴 상태가 된다")
  void withdrawMember() {
    // given
    Member member = new Member(1L, Nickname.of("elton"), "MALE", "1995");

    // when
    member.withdraw();

    // then
    assertThat(member.isWithdrawn()).isTrue();
  }

  @Test
  @DisplayName("닉네임을 변경할 수 있다")
  void updateNickname() {
    // given
    Member member = new Member(1L, Nickname.of("elton"), "MALE", "1995");
    String newNickname = "newNickname";

    // when
    member.updateNickname(newNickname);

    // then
    assertThat(member.getNickname().toString()).isEqualTo(newNickname);
  }
}