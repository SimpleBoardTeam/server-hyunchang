package com.simpleboard.board.auth.domain.token.util;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Component;

/**
 * <b>IdGenerator</b> Utility.
 *
 * <p>암호학적 난수를 이용한 256-bit 토큰 ID 생성
 *
 * @domain util
 * @since 1.0
 */
@Component
public class IdGenerator {
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  /**
   * <b>새 토큰 ID 생성</b>
   *
   * <p>URL-safe Base64(패딩 제거)로 인코딩
   *
   * @return 토큰 ID
   * @since 1.0
   */
  public String newTokenId() {
    byte[] buf = new byte[32]; // 256-bit
    SECURE_RANDOM.nextBytes(buf);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
  }
}
