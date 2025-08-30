package com.simpleboard.board.auth.domain.auth.dto.request;

import com.simpleboard.board.auth.domain.auth.vo.RegisterType;
import com.simpleboard.board.auth.domain.common.vo.Role;
import lombok.Builder;

/**
 * 회원가입 요청 모델.
 *
 * <p>Req: application -> domain
 *
 * @domain request-dto
 */
@Builder
public record RegisterParams(
    Role role,
    RegisterType registerType,
    String email, // OAuth 가입시 null
    String password, // OAuth 가입시 null
    String OAuthId // email 가입시 null
    ) {}
