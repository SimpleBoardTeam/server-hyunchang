package com.simpleboard.board.auth.application.service;

import com.simpleboard.board.auth.application.dto.request.EmailMemberRegisterCommand;
import com.simpleboard.board.auth.application.dto.request.OAuthMemberRegisterCommand;

/**
 * <b>UserAuth aggregate에 대한 응용 서비스 클래스</b>
 *
 * <ul>
 *   <li>회원가입 시 UserAuth의 생성 & Member 도메인에 생성 이벤트 발송
 *   <li>회원가입 과정에서 Auth aggregate에 대한 조회, 검증 수행
 * </ul>
 *
 * @domain application-service
 */
public interface UserAuthCommandService {

  /**
   * <b>Email 회원가입을 수행하는 메서드</b>
   *
   * <ul>
   *   <li>command에 존재하는 토큰 검증&파싱
   *   <li>Auth aggregate 생성, 저장
   *   <li>email Member 회원가입 이벤트 생성
   * </ul>
   *
   * @param command 회원가입 command
   */
  public void emailMemberRegister(EmailMemberRegisterCommand command);

  /**
   * <b>OAuth 회원가입을 수행하는 메서드</b>
   *
   * <ul>
   *   <li>command에 존재하는 토큰 검증&파싱
   *   <li>Auth aggregate 생성, 저장
   *   <li>OAuth Member 회원가입 이벤트 생성
   * </ul>
   *
   * @param command 회원가입 command
   */
  public void oAuthMemberRegister(OAuthMemberRegisterCommand command);

  /**
   * <b>회원 탈퇴 응용서비스</b>
   *
   * <p>Auth aggregate를 비활성화 후 이벤트 생성
   */
  public void deactivateAccount(Long memberId);
}
