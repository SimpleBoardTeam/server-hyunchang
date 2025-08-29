package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.dto.response.AuthorSummary;
import java.util.List;

/**
 * <b>Member Data 조회 서비스 클래스</b>
 *
 * <p>Member B.C의 internal API를 통해 데이터를 Fetch
 */
public interface MemberFetchService {
  /**
   * <b>Author summary 단일 조회 메서드</b>
   *
   * @since 1.0
   */
  AuthorSummary fetchAuthorSummary(Long authorId);

  /**
   * <b>Author summary 다중 조회 메서드</b>
   *
   * @since 1.0
   */
  List<AuthorSummary> fetchAuthorSummaryList(List<Long> authorIds);
}
