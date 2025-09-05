package com.simpleboard.board.member.infrastructure.jpa.converter;

import com.simpleboard.board.member.domain.Nickname;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class NicknameConverter implements AttributeConverter<Nickname, String> {

  @Override
  public String convertToDatabaseColumn(Nickname attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public Nickname convertToEntityAttribute(String dbData) {
    return dbData == null ? null : Nickname.of(dbData);
  }
}
