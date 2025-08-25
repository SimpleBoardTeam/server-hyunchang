package com.simpleboard.board.member.application.command.event;

public record MemberUpdateNicknameEvent(Long memberId, String nickname) {}
