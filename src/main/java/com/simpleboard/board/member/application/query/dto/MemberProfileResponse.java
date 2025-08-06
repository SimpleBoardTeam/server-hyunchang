package com.simpleboard.board.member.application.query.dto;

import com.simpleboard.board.member.domain.Gender;
import com.simpleboard.board.member.domain.Member;
import java.time.LocalDateTime;

public record MemberProfileResponse(
    Long memberId, String nickname, Gender gender, String birthYear, LocalDateTime signUpDate) {

  public static MemberProfileResponse from(Member member) {
    return new MemberProfileResponse(
        member.getMemberId(),
        member.getNickname().toString(),
        member.getGender(),
        member.getBirthYear().toString(),
        member.getSignUpDate());
  }
}
