package com.simpleboard.board.global.config;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (ex, method, params) -> {
      log.error(
          "[ASYNC EXCEPTION] method={} args={} type={} msg={}",
          method.getName(),
          Arrays.toString(params),
          ex.getClass().getSimpleName(),
          ex.getMessage(),
          ex
      );
    };
  }
}
