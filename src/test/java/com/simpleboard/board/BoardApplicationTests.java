package com.simpleboard.board;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("CI 환경에서는 전체 테스트 비활성화")
class BoardApplicationTests {

  @Test
  void contextLoads() {}
}
