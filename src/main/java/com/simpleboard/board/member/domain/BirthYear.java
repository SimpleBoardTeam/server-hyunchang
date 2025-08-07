package com.simpleboard.board.member.domain;

import com.simpleboard.board.member.domain.exception.InvalidBirthYearException;
import java.time.Year;
import lombok.EqualsAndHashCode;

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