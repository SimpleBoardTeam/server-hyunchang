package com.simpleboard.board.member.domain;

import com.simpleboard.board.member.domain.exception.InvalidBirthYearException;
import java.time.Year;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class BirthYear {

  private final String birthYear;

  private BirthYear(String birthYear) {
    this.birthYear = birthYear;
  }

  public static BirthYear of(String input) {
    validate(input);
    return new BirthYear(input);
  }

  public static void validate(String input) {

    if (!input.matches("\\d{4}")) {
      throw new InvalidBirthYearException("출생 연도는 4자리 숫자여야 합니다.");
    }

    int year = Integer.parseInt(input);
    int currentYear = Year.now().getValue();

    if (year < 1900 || year > currentYear) {
      throw new InvalidBirthYearException();
    }
  }

  @Override
  public String toString() {
    return birthYear;
  }
}
