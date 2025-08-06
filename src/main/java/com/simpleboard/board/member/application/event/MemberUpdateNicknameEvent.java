package com.simpleboard.board.member.application.event;

public record MemberUpdateNicknameEvent(Long memberId, String nickname) {
}