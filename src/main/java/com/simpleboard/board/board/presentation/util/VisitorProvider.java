package com.simpleboard.board.board.presentation.util;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * <b>Visitor 제공 유틸리티 클래스</b>
 *
 * <p>Request의 인증 정보를 기반으로 Visitor 클래스 생성
 *
 * @since 1.0
 */
@Component
public class VisitorProvider {

  private final String FINGERPRINT = "fingerprint";
  private final String ROLE_GUEST = "ROLE_GUEST";

  /**
   * <b>Visitor 생성 메서드</b>
   *
   * <p>Security context holder에서 Authentication 정보를 추출
   *
   * <ul>
   *   <li>Fingerprint를 추춣하여 vid 주입
   *   <li>Member와 Guest를 구별해 Visitor 구성
   * </ul>
   *
   * @since 1.0
   */
  public Visitor getVisitor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // 기본값: 비회원
    Visitor.VisitorBuilder builder = Visitor.builder().type(VisitorType.GUEST);

    // fingerprint 추출
    if (authentication != null) {
      Object details = authentication.getDetails();
      if (details instanceof Map<?, ?> detailsMap) {
        Object fp = detailsMap.get(FINGERPRINT);
        if (fp != null) builder.vId(String.valueOf(fp));
      }
    }

    // memberId 추출
    if (isMember(authentication)) {
      String strId = authentication.getName();
      Long memberId = tryParseLong(strId);
      if (memberId != null) {
        builder.type(VisitorType.MEMBER).memberId(memberId);
      }
    }

    return builder.build();
  }

  private boolean isMember(Authentication auth) {
    if (auth == null) return false;

    if (auth instanceof AnonymousAuthenticationToken) return false;
    if (auth instanceof UserDetails) return true;

    // 권한에 ROLE_GUEST만 있으면 비회원으로 취급
    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
    boolean onlyGuest =
        authorities != null
            && !authorities.isEmpty()
            && authorities.stream().allMatch(a -> Objects.equals(a.getAuthority(), ROLE_GUEST));
    if (onlyGuest) return false;

    // 그 외는 로그인 사용자로 간주
    return false;
  }

  private Long tryParseLong(String s) {
    try {
      return Long.parseLong(s);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
