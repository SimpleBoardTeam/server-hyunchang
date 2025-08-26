package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.converter.PostQueryServiceRepoConverter;
import com.simpleboard.board.board.application.dto.request.PostDetailsQuery;
import com.simpleboard.board.board.application.dto.request.PostListQuery;
import com.simpleboard.board.board.application.dto.response.AuthorSummary;
import com.simpleboard.board.board.application.dto.response.PostDetailsQueryResult;
import com.simpleboard.board.board.application.dto.response.PostListQueryResult;
import com.simpleboard.board.board.application.exception.BoardNameNotFound;
import com.simpleboard.board.board.application.query.BoardQueryRepository;
import com.simpleboard.board.board.application.query.PostDetailsReadModel;
import com.simpleboard.board.board.application.query.PostListReadModel;
import com.simpleboard.board.board.application.query.PostQueryRepository;
import com.simpleboard.board.board.domain.post.exception.PostNotFoundException;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

  private final PostQueryRepository postQueryRepository;
  private final BoardQueryRepository boardQueryRepository;
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

  @Override
  public PostListQueryResult getPostList(PostListQuery query) {
    // board name 조회
    String boardName =
        boardQueryRepository.findBoardNameById(query.boardId()).orElseThrow(BoardNameNotFound::new);

    // post 목록 조회
    PostListReadModel readModel = postQueryRepository.getPostList(converter.getCriteria(query));

    // 목록 내 member post의 nickname 조회
    List<Long> authorIds =
        readModel.posts().stream()
            .filter(p -> p.postType().equals(PostTypeEnum.MEMBER))
            .map(p -> p.authorId())
            .toList();
    Map<Long, String> nicknameMap =
        memberFetchService.fetchAuthorSummaryList(authorIds).stream()
            .collect(Collectors.toMap(AuthorSummary::authorId, summary -> summary.nickname()));

    return converter.getQueryResult(query.boardId(), boardName, readModel, nicknameMap);
  }
}
