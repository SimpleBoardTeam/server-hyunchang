package com.simpleboard.board.member.infrastructure.jpa.entity;

import com.simpleboard.board.member.domain.BirthYear;
import com.simpleboard.board.member.domain.Gender;
import com.simpleboard.board.member.domain.Nickname;
import com.simpleboard.board.member.infrastructure.jpa.converter.BirthYearConverter;
import com.simpleboard.board.member.infrastructure.jpa.converter.NicknameConverter;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MemberEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Convert(converter = NicknameConverter.class)
  @Column(name = "nickname", nullable = false, length = 50)
  private Nickname nickname;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", nullable = false, length = 20)
  private Gender gender;

  @Convert(converter = BirthYearConverter.class)
  @Column(name = "birth_year", nullable = false)
  private BirthYear birthYear;

  @Column(name = "sign_up_date", nullable = false)
  private LocalDateTime signUpDate;

  @Column(name = "is_withdrawn", nullable = false)
  private boolean withdrawn;

  public MemberEntity(
      Long id,
      Nickname nickname,
      Gender gender,
      BirthYear birthYear,
      LocalDateTime signUpDate,
      boolean withdrawn) {
    this.id = id;
    this.nickname = nickname;
    this.gender = gender;
    this.birthYear = birthYear;
    this.signUpDate = signUpDate;
    this.withdrawn = withdrawn;
  }
}
