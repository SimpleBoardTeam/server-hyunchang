package com.simpleboard.board.member.application.command.dto;

import com.simpleboard.board.member.domain.Gender;

public record MemberRegisterCommand(
    Long memberId, String nickname, Gender gender, Integer birthYear) {}
