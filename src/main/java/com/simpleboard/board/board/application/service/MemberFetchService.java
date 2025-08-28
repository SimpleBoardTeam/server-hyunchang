package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.dto.response.AuthorSummary;

public interface MemberFetchService {
  AuthorSummary fetchAuthorSummary(Long authorId);
}
