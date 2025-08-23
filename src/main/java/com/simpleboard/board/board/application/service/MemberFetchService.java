package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.dto.response.AuthorSummary;

/**
 * <b>Member Data 조회 서비스 클래스</b>
 *
 * <p>Member B.C의 internal API를 통해 데이터를 Fetch
 */
public interface MemberFetchService {
  AuthorSummary getAuthorSummary(Long authorId);
}
