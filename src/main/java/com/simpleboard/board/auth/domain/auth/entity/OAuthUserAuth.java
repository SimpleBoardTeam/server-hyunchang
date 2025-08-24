package com.simpleboard.board.auth.domain.auth.entity;

import com.simpleboard.board.auth.domain.auth.dto.request.RegisterParams;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * <b>OAuth Authentication</b> Aggregate Root.
 *
 * <p>OAuth 회원가입 유저의 인증/인가 관련 로직 수행
 *
 * @domain aggregate-root
 * @since 1.0
 */
@Getter
@SuperBuilder
public class OAuthUserAuth extends UserAuth {
  private String OAuthId;

  @Override
  public String getLoginId() {
    return OAuthId;
  }

  @Override
  public String getPassword() {
    return "-";
  }

  protected OAuthUserAuth(RegisterParams params) {
    super(params);
    this.OAuthId = params.OAuthId();
  }
}
