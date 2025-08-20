package com.simpleboard.board.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.simpleboard.board.member.domain.exception.DuplicatedNicknameException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberTest {

  @Mock NicknamePolicy nicknamePolicy;

  @Test
  @DisplayName("정상적으로 Member를 생성할 수 있다")
  void createMember() {
    // given
    doNothing().when(nicknamePolicy).ensureUnique(any(Nickname.class));

    Long memberId = 1L;
    Nickname nickname = Nickname.of("nickname");
    Gender gender = Gender.MALE;
    BirthYear birthYear = BirthYear.of("1998");
    LocalDateTime now = LocalDateTime.now();

    // when
    Member member = Member.create(memberId, nickname, gender, birthYear, now, nicknamePolicy);

    // then
    assertThat(member.getMemberId()).isEqualTo(memberId);
    assertThat(member.getNickname()).isEqualTo(nickname);
    assertThat(member.getGender()).isEqualTo(Gender.MALE);
    assertThat(member.getBirthYear().getValue()).isEqualTo(1998);
    assertThat(member.getSignUpDate()).isEqualTo(now);
    assertThat(member.isWithdrawn()).isFalse();

    verify(nicknamePolicy).ensureUnique(nickname); // isUnique 아님!
    verifyNoMoreInteractions(nicknamePolicy);
  }

  @Test
  @DisplayName("Member 생성 시 signUpDate를 now(clock)으로 설정할 수 있다")
  void signUpDateFromClock() {
    doNothing().when(nicknamePolicy).ensureUnique(any(Nickname.class));

    Clock fixedClock = Clock.fixed(Instant.parse("2020-01-01T00:00:00Z"), ZoneId.of("UTC"));
    LocalDateTime expected = LocalDateTime.ofInstant(fixedClock.instant(), fixedClock.getZone());

    Member member =
        Member.create(
            1L,
            Nickname.of("nickname"),
            Gender.FEMALE,
            BirthYear.of("1998"),
            LocalDateTime.now(fixedClock),
            nicknamePolicy);

    assertThat(member.getSignUpDate()).isEqualTo(expected);
    verify(nicknamePolicy).ensureUnique(any(Nickname.class));
    verifyNoMoreInteractions(nicknamePolicy);
  }

  @Test
  @DisplayName("Member는 withdraw() 호출 시 탈퇴 상태가 된다(멱등)")
  void withdrawMember() {
    doNothing().when(nicknamePolicy).ensureUnique(any(Nickname.class));

    Member member =
        Member.create(
            1L,
            Nickname.of("nickname"),
            Gender.MALE,
            BirthYear.of("1998"),
            LocalDateTime.now(),
            nicknamePolicy);

    member.withdraw();
    member.withdraw(); // 멱등 확인

    assertThat(member.isWithdrawn()).isTrue();
    verify(nicknamePolicy).ensureUnique(any(Nickname.class));
    verifyNoMoreInteractions(nicknamePolicy);
  }

  @Test
  @DisplayName("닉네임을 변경할 수 있다")
  void updateNickname() {
    doNothing().when(nicknamePolicy).ensureUnique(any(Nickname.class));

    Member member =
        Member.create(
            1L,
            Nickname.of("nickname"),
            Gender.MALE,
            BirthYear.of("1995"),
            LocalDateTime.now(),
            nicknamePolicy);

    Nickname newNickname = Nickname.of("newNickname");

    member.updateNickname(newNickname, nicknamePolicy);

    assertThat(member.getNickname()).isEqualTo(newNickname);

    InOrder inOrder = inOrder(nicknamePolicy);
    inOrder.verify(nicknamePolicy).ensureUnique(Nickname.of("nickname"));
    inOrder.verify(nicknamePolicy).ensureUnique(newNickname);
    verifyNoMoreInteractions(nicknamePolicy);
  }

  @Test
  @DisplayName("닉네임이 중복이면 예외가 발생한다")
  void updateNickname_throwsOnDuplicate() {
    doNothing().when(nicknamePolicy).ensureUnique(any(Nickname.class));
    Member member =
        Member.create(
            1L,
            Nickname.of("nickname"),
            Gender.MALE,
            BirthYear.of("1998"),
            LocalDateTime.now(),
            nicknamePolicy);

    // 이미 존재하는 이름
    Nickname dup = Nickname.of("alreadyExistsNickname");
    doThrow(new DuplicatedNicknameException()).when(nicknamePolicy).ensureUnique(dup);

    assertThatThrownBy(() -> member.updateNickname(dup, nicknamePolicy))
        .isInstanceOf(DuplicatedNicknameException.class);

    verify(nicknamePolicy).ensureUnique(Nickname.of("nickname"));
    verify(nicknamePolicy).ensureUnique(dup);
    verifyNoMoreInteractions(nicknamePolicy);
  }
}
