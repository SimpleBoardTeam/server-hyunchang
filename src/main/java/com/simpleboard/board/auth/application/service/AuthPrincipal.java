package com.simpleboard.board.auth.application.service;

import com.simpleboard.board.auth.domain.auth.entity.UserAuth;
import com.simpleboard.board.auth.domain.auth.vo.RegisterType;
import com.simpleboard.board.auth.domain.auth.vo.UserState;
import com.simpleboard.board.auth.domain.common.vo.Role;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <b>Security context에 주입될 Authentication 정보</b>
 *
 * <p>UserDetails 인터페이스를 구현
 *
 * <p>email 로그인, Access 토큰 파싱시 사용
 */
public class AuthPrincipal implements UserDetails {

  @Getter private final Long userId;
  @Getter private final Role role;
  private final String password;
  private final UserState userState;
  private final RegisterType registerType;

  public AuthPrincipal(UserAuth userAuth) {
    this.userId = userAuth.getUserId();
    this.password = userAuth.getPassword();
    this.role = userAuth.getRole();
    this.userState = userAuth.getUserState();
    this.registerType = userAuth.getRegisterType();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getUsername() {
    return String.valueOf(userId);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return userState == UserState.ACTIVE;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return userState == UserState.ACTIVE;
  }
}
