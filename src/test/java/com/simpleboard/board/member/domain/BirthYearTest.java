package com.simpleboard.board.member.domain;

import com.simpleboard.board.member.domain.exception.InvalidBirthYearException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.assertj.core.api.Assertions.*;

class BirthYearTest {

  @Test
  @DisplayName("정상적인 4자리 출생 연도로 BirthYear를 생성할 수 있다")
  void createValidBirthYear() {
    // given
    String input = "1995";

    // when
    BirthYear birthYear = BirthYear.of(input);

    // then
    assertThat(birthYear.getValue()).isEqualTo(1995);
  }

  @Test
  @DisplayName("4자리가 아닌 경우 예외가 발생한다")
  void createBirthYearWithInvalidFormat() {
    // given
    String input = "95";

    // expect
    assertThatThrownBy(() -> BirthYear.of(input))
        .isInstanceOf(InvalidBirthYearException.class)
        .hasMessageContaining("4자리 숫자");
  }

  @Test
  @DisplayName("출생 연도가 1900년 이전이면 예외가 발생한다")
  void createBirthYearBefore1900() {
    // given
    String input = "1899";

    // expect
    assertThatThrownBy(() -> BirthYear.of(input))
        .isInstanceOf(InvalidBirthYearException.class)
        .hasMessageContaining("유효하지 않습니다");
  }

  @Test
  @DisplayName("출생 연도가 현재 연도보다 크면 예외가 발생한다")
  void createBirthYearAfterCurrentYear() {
    // given
    int nextYear = Year.now().getValue() + 1;
    String input = String.valueOf(nextYear);

    // expect
    assertThatThrownBy(() -> BirthYear.of(input))
        .isInstanceOf(InvalidBirthYearException.class)
        .hasMessageContaining("유효하지 않습니다");
  }

  @Test
  @DisplayName("BirthYear는 같은 값이면 동등하게 비교된다")
  void equalsAndHashCode() {
    // given
    BirthYear b1 = BirthYear.of("2000");
    BirthYear b2 = BirthYear.of("2000");

    // then
    assertThat(b1).isEqualTo(b2);
    assertThat(b1.hashCode()).isEqualTo(b2.hashCode());
  }
}