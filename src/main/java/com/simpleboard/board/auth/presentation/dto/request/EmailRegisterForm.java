package com.simpleboard.board.auth.presentation.dto.request;

/**
 * Email 회원 가입 요청 모델.
 *
 * <p>Req: Client -> Presentation
 *
 * @domain request-dto
 */
public record EmailRegisterForm(String password, String gender, int birthYear) {}
