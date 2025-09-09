package com.simpleboard.board.board.application.converter;

import com.simpleboard.board.board.application.dto.request.CommentListQuery;
import com.simpleboard.board.board.application.dto.response.CommentListQueryResult;
import com.simpleboard.board.board.application.query.CommentListCriteria;
import com.simpleboard.board.board.application.query.CommentListReadModel;
import org.springframework.stereotype.Component;

/**
 * <b>Post query service <-> repository 컨버터</b>
 *
 * @version 1.0
 */
@Component
public class CommentQueryServiceRepoConverter {

  /**************************
   * Request converter
   * Service -> Repository
   **************************/

  public CommentListCriteria getCriteria(CommentListQuery query) {
    return null;
  }

  /**************************
   * Response converter
   * Service <- Repository
   **************************/

  public CommentListQueryResult getQueryResult(CommentListReadModel readModel) {
    return null;
  }
}
