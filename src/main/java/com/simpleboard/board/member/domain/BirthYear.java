package com.simpleboard.board.member.domain;

import com.simpleboard.board.member.domain.exception.InvalidBirthYearException;
import java.time.Year;
import lombok.EqualsAndHashCode;

/**
 * <b>BirthYear</b> Value Object.
 *
 * <p>회원의 출생 연도를 캡슐화한 VO로,
 * 4자리 숫자 형식과 허용 범위(1900년~현재 연도)를 검증하여
 * 유효한 출생 연도만 생성되도록 보장한다.</p>
 *
 * @domain value-object
 * @since 1.0
 */
@EqualsAndHashCode
public class BirthYear {

  private final Integer birthYear;

  private BirthYear(Integer birthYear) {
    this.birthYear = birthYear;
  }

  public static BirthYear of(String input) {
    validate(input);
    return new BirthYear(Integer.parseInt(input));
  }

  private static void validate(String input) {
    if (!input.matches("\\d{4}")) {
      throw new InvalidBirthYearException("출생 연도는 4자리 숫자여야 합니다.");
    }

    int year = Integer.parseInt(input);
    int currentYear = Year.now().getValue();

    if (year < 1900 || year > currentYear) {
      throw new InvalidBirthYearException("출생 연도가 유효하지 않습니다.");
    }
  }

  public Integer getValue() {
    return birthYear;
  }

  @Override
  public String toString() {
    return String.valueOf(birthYear);
  }
}