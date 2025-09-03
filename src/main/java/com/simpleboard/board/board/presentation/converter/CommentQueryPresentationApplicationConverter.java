package com.simpleboard.board.board.presentation.converter;

import com.simpleboard.board.board.application.dto.request.CommentListQuery;
import com.simpleboard.board.board.application.dto.response.CommentListQueryResult;
import com.simpleboard.board.board.presentation.dto.request.CommentListQueryForm;
import com.simpleboard.board.board.presentation.dto.response.CommentListQueryResponse;
import org.springframework.stereotype.Component;

/**
 * <b>Post query presentation <-> application 컨버터</b>
 *
 * @version 1.0
 */
@Component
public class CommentQueryPresentationApplicationConverter {

  /**************************
   * Request converter
   * presentation -> Service
   **************************/

  public CommentListQuery toQuery(CommentListQueryForm form) {
    return null;
  }

  /**************************
   * Response converter
   * presentation <- Service
   **************************/

  public CommentListQueryResponse toQueryResponse(CommentListQueryResult result) {
    return null;
  }
}
