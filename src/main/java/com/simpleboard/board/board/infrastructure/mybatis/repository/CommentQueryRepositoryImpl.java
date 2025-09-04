package com.simpleboard.board.board.infrastructure.mybatis.repository;

import com.simpleboard.board.board.application.query.CommentListCriteria;
import com.simpleboard.board.board.application.query.CommentListReadModel;
import com.simpleboard.board.board.application.query.CommentQueryRepository;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.CommentQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {
  private final CommentQueryMapper commentQueryMapper;

  @Override
  public CommentListReadModel getCommentList(CommentListCriteria criteria) {
    return null;
  }
}
