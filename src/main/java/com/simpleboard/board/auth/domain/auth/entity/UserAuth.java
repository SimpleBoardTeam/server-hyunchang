package com.simpleboard.board.auth.domain.auth.entity;

import com.simpleboard.board.auth.application.exception.UserDisabledException;
import com.simpleboard.board.auth.domain.auth.dto.request.RegisterParams;
import com.simpleboard.board.auth.domain.auth.vo.RegisterType;
import com.simpleboard.board.auth.domain.auth.vo.UserState;
import com.simpleboard.board.auth.domain.common.vo.Role;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * <b>Authentication aggregate</b> Aggregate Root.
 *
 * <p>Auth 클래스의 공통정보를 가지는 abstract class
 *
 * <p>Authentication과 관련된 로직을 수행
 *
 * @domain aggregate-root
 * @since 1.0
 */
@Getter
@SuperBuilder
public abstract class UserAuth {
  private Long userId;
  private UserState userState;
  private RegisterType registerType;
  private Role role;

  protected UserAuth(RegisterParams params) {
    userState = UserState.ACTIVE;
    registerType = params.registerType();
    role = params.role();
  }

  /**
   * <b>회원 탈퇴 메서드</b>
   *
   * <p>ACTIVE 상태의 유저를 DELETED 상태로 변환(soft deletion)
   *
   * @throws UserDisabledException - 유저가 ACTIVE 상태가 아님
   * @since 1.0
   */
  public void delete() {
    if (userState != UserState.ACTIVE) throw new UserDisabledException();
    userState = UserState.DELETED;
  }

  /**
   * <b>Log in 시 사용되는 identifier 반환 메서드</b>
   *
   * <p>email 회원가입 유저는 email을, OAuth 회원가입 유저는 OAuth id 반환
   *
   * @since 1.0
   */
  public abstract String getLoginId();

  /**
   * <b>회원의 비밀번호 반환 메서드</b>
   *
   * <p>email 회원가입 유저는 password를, OAuth 회원가입 유저는 "-" 반환
   *
   * @since 1.0
   */
  public abstract String getPassword();

  /**
   * <b>신규 회원가입 수행 메서드</b>
   *
   * <p>register type에 맞는 sub class를 생성해 반환
   *
   * @since 1.0
   */
  public static UserAuth register(RegisterParams params) {
    switch (params.registerType()) {
      case EMAIL:
        return new EmailUserAuth(params);
      case GOOGLE:
      case KAKAO:
        return new OAuthUserAuth(params);
    }
    throw new IllegalArgumentException("Unsupported auth type: " + params.registerType());
  }
}
