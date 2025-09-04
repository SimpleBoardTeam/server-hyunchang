package com.simpleboard.board.auth.infrastructure.config;

import java.time.Clock;
import java.time.ZoneId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <b>SystemClock에서 사용하는 Clock 설정 클래스</b>
 *
 * <p>ClockManager 인터페이스에서 사용
 *
 * <p>동경시 반환
 */
@Configuration
public class TimeConfig {
  @Bean
  public Clock clock() {
    return Clock.system(ZoneId.of("JST"));
  }
}
