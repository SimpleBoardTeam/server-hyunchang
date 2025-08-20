package com.simpleboard.board.member.application.query.dto;

import com.simpleboard.board.member.domain.Gender;
import java.time.LocalDateTime;

public record MemberProfileResponse(
    Long memberId, String nickname, Gender gender, String birthYear, LocalDateTime signUpDate) {

  public static MemberProfileResponse from(MemberFullView memberFullView) {
    return new MemberProfileResponse(
        memberFullView.memberId(),
        memberFullView.nickname(),
        memberFullView.gender(),
        memberFullView.birthYear(),
        memberFullView.signUpDate());
  }
}
