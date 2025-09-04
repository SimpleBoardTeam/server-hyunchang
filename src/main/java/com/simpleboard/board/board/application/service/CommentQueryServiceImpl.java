package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.converter.CommentQueryServiceRepoConverter;
import com.simpleboard.board.board.application.dto.request.CommentListQuery;
import com.simpleboard.board.board.application.dto.response.CommentListQueryResult;
import com.simpleboard.board.board.application.query.CommentQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentQueryServiceImpl implements CommentQueryService {
  private final CommentQueryRepository commentQueryRepository;
  private final CommentQueryServiceRepoConverter converter;
  private final MemberFetchService memberFetchService;

  @Override
  public CommentListQueryResult getCommentList(CommentListQuery query) {
    return null;
  }
}
