package com.simpleboard.board.auth.application.dto.request;

import lombok.Builder;

@Builder
public record OAuthMemberRegisterCommand(String nicknameTokenRaw, String gender, int birthYear) {}
