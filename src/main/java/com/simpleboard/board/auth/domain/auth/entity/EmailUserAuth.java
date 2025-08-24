package com.simpleboard.board.auth.domain.auth.entity;

import com.simpleboard.board.auth.domain.auth.dto.request.RegisterParams;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * <b>Email Authentication</b> Aggregate Root.
 *
 * <p>email 회원가입 유저의 인증/인가 관련 로직 수행
 *
 * @domain aggregate-root
 * @since 1.0
 */
@Getter
@SuperBuilder
public class EmailUserAuth extends UserAuth {
  private String email;
  private String password;

  @Override
  public String getLoginId() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  protected EmailUserAuth(RegisterParams params) {
    super(params);
    this.email = params.email();
    this.password = params.password();
  }
}
