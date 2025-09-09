package com.simpleboard.board.member.infrastructure.jpa.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.simpleboard.board.member.domain.BirthYear;
import com.simpleboard.board.member.domain.Gender;
import com.simpleboard.board.member.domain.Member;
import com.simpleboard.board.member.domain.Nickname;
import com.simpleboard.board.member.infrastructure.jpa.entity.MemberEntity;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MemberMapperTest {

  private MemberMapper memberMapper;

  @BeforeEach
  void setUp() {
    memberMapper = new MemberMapper();
  }

  @Test
  @DisplayName("toEntity: Member -> MemberEntity")
  void toEntity() {
    // given
    Member member = Mockito.mock(Member.class);
    Long id = 1L;
    Nickname nickname = Nickname.of("elton");
    Gender gender = Gender.MALE;
    BirthYear birthYear = BirthYear.of(1997);
    LocalDateTime signUpDate = LocalDateTime.of(2025, 8, 29, 12, 0, 0);
    boolean withdrawn = false;

    Mockito.when(member.getMemberId()).thenReturn(id);
    Mockito.when(member.getNickname()).thenReturn(nickname);
    Mockito.when(member.getGender()).thenReturn(gender);
    Mockito.when(member.getBirthYear()).thenReturn(birthYear);
    Mockito.when(member.getSignUpDate()).thenReturn(signUpDate);
    Mockito.when(member.isWithdrawn()).thenReturn(withdrawn);

    // when
    MemberEntity entity = memberMapper.toEntity(member);

    // then
    assertThat(entity.getId()).isEqualTo(id);
    assertThat(entity.getNickname()).isEqualTo(nickname);
    assertThat(entity.getGender()).isEqualTo(gender);
    assertThat(entity.getBirthYear()).isEqualTo(birthYear);
    assertThat(entity.getSignUpDate()).isEqualTo(signUpDate);
    assertThat(entity.isWithdrawn()).isFalse();
  }

  @Test
  @DisplayName("toDomain: MemberEntity -> Member")
  void toDomain() {
    // given
    MemberEntity entity =
        new MemberEntity(
            10L,
            Nickname.of("winter"),
            Gender.FEMALE,
            BirthYear.of(2001),
            LocalDateTime.of(2024, 12, 31, 23, 59, 59),
            true);

    // when
    Member domain = memberMapper.toDomain(entity);

    // then
    assertThat(domain.getMemberId()).isEqualTo(10L);
    assertThat(domain.getNickname()).isEqualTo(Nickname.of("winter"));
    assertThat(domain.getGender()).isEqualTo(Gender.FEMALE);
    assertThat(domain.getBirthYear()).isEqualTo(BirthYear.of(2001));
    assertThat(domain.getSignUpDate()).isEqualTo(LocalDateTime.of(2024, 12, 31, 23, 59, 59));
    assertThat(domain.isWithdrawn()).isTrue();
  }
}
