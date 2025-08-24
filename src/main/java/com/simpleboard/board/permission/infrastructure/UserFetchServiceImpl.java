package com.simpleboard.board.permission.infrastructure;

import com.simpleboard.board.permission.application.common.UserFetchService;
import org.springframework.stereotype.Service;

@Service
public class UserFetchServiceImpl implements UserFetchService {

  @Override
  public Long getUserIdByNickname(String nickname) {
    return 1L;
  }
}
