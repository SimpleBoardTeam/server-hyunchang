package com.simpleboard.board.member.application.query.dto;

import com.simpleboard.board.member.domain.Gender;
import java.time.LocalDateTime;

public record MemberFullView(
    Long memberId, String nickname, Gender gender, Integer birthYear, LocalDateTime signUpDate) {}
