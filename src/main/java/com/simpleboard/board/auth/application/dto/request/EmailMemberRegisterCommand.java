package com.simpleboard.board.auth.application.dto.request;

import lombok.Builder;

@Builder
public record EmailMemberRegisterCommand(
    String emailTokenRaw, String nicknameTokenRaw, String password, String gender, int birthYear) {}
