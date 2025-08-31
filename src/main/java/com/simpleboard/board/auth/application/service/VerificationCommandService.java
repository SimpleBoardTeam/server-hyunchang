package com.simpleboard.board.auth.application.service;

import com.simpleboard.board.auth.application.exception.EmailCodeException;
import com.simpleboard.board.auth.application.exception.EmailConflictException;
import com.simpleboard.board.auth.application.exception.NicknameConflictException;
import com.simpleboard.board.auth.domain.token.vo.Token;

/** <b>여러 Verification을 처리하는 메서드</b> */
public interface VerificationCommandService {

  /**
   * <b>Email 회원가입 시 Email 사용 가능 검증 메서드</b>
   *
   * <p>email이 사용 가능한지 검증 후 email에 코드 발송
   *
   * @exception EmailConflictException 이미 가입된 이메일
   * @param email Code를 발송할 email
   */
  public void sendEmailCode(String email);

  /**
   * <b>Email 회원가입 시 Email 사용 가능 검증 메서드</b>
   *
   * <p>email에 발송한 코드를 검증 후 코드 일치 시 lock & 토큰 반환
   *
   * @exception EmailCodeException 코드 불일치
   * @param email 검증 email
   * @return email 토큰
   */
  public Token verifyEmailCode(String email, String code);

  /**
   * <b>nickname verification 메서드</b>
   *
   * <p>대상 nickname이 사용 가능한 닉네임인지 확인 후 사용 가능하다면 lock & 토큰 반환
   *
   * @exception NicknameConflictException nickname이 이미 사용중임
   * @param nickname 검증 nickname
   * @return nickname 토큰
   */
  public Token verifyNickname(String nickname);
}
