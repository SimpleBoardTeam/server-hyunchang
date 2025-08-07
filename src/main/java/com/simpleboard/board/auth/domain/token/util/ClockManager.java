package com.simpleboard.board.auth.domain.token.util;

import java.time.Instant;

/**
 * <b>ClockManager</b> Utility.
 *
 * <p>UTC 시스템 클록을 이용해 현재 시각 제공
 *
 * @domain util
 * @since 1.0
 */
public interface ClockManager {
  Instant now();
}
