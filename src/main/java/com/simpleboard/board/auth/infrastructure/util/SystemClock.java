package com.simpleboard.board.auth.infrastructure.util;

import com.simpleboard.board.auth.domain.token.util.ClockManager;
import java.time.Clock;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class SystemClock implements ClockManager {
  private final Clock clock;

  public SystemClock(Clock clock) {
    this.clock = clock;
  }

  @Override
  public Instant now() {
    return Instant.now(clock);
  }
}
