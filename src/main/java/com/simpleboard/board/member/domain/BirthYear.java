package com.simpleboard.board.member.domain;

import com.simpleboard.board.member.domain.exception.InvalidBirthYearException;
import java.time.Year;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <b>BirthYear</b> Value Object.
 *
 * <p>회원의 출생 연도(정수)를 캡슐화한 VO로, 허용 범위(1900년 ~ 현재 연도)를 검증하여 유효한 값만 생성되도록 보장한다.
 *
 * @domain value-object
 * @since 1.0
 */
@Getter
@EqualsAndHashCode
public class BirthYear {

  private final Integer value;

  private BirthYear(Integer value) {
    this.value = value;
  }

  public static BirthYear of(Integer year) {
    validate(year);
    return new BirthYear(year);
  }

  private static void validate(Integer year) {
    if (year == null) {
      throw new InvalidBirthYearException("출생 연도가 비어 있습니다.");
    }
    // 4자리 체크
    if (year < 1000 || year > 9999) {
      throw new InvalidBirthYearException("출생 연도는 4자리여야 합니다.");
    }

    int currentYear = Year.now().getValue();
    if (year < 1900 || year > currentYear) {
      throw new InvalidBirthYearException("출생 연도가 유효하지 않습니다.");
    }
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
