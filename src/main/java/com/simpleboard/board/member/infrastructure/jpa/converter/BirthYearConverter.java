package com.simpleboard.board.member.infrastructure.jpa.converter;

import com.simpleboard.board.member.domain.BirthYear;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class BirthYearConverter implements AttributeConverter<BirthYear, Integer> {

  @Override
  public Integer convertToDatabaseColumn(BirthYear attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public BirthYear convertToEntityAttribute(Integer dbData) {
    return dbData == null ? null : BirthYear.of(dbData);
  }
}
