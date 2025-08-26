package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.converter.PostQueryServiceRepoConverter;
import com.simpleboard.board.board.application.dto.request.PostDetailsQuery;
import com.simpleboard.board.board.application.dto.response.AuthorSummary;
import com.simpleboard.board.board.application.dto.response.PostDetailsQueryResult;
import com.simpleboard.board.board.application.query.PostDetailsReadModel;
import com.simpleboard.board.board.application.query.PostQueryRepository;
import com.simpleboard.board.board.domain.post.exception.PostNotFoundException;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

  private final PostQueryRepository postQueryRepository;
  private final MemberFetchService memberFetchService;
  private final PostQueryServiceRepoConverter converter;

  @Override
  public PostDetailsQueryResult getPostDetails(PostDetailsQuery query) {
    PostDetailsReadModel readModel =
        postQueryRepository.getPostDetails(converter.getCriteria(query));
    if (readModel == null) throw new PostNotFoundException();
    if (readModel.postType().equals(PostTypeEnum.MEMBER)) {
      AuthorSummary summary = memberFetchService.getAuthorSummary(readModel.authorId());
      return converter.getQueryResult(readModel, summary);
    }
    return converter.getQueryResult(readModel);
  }
}
