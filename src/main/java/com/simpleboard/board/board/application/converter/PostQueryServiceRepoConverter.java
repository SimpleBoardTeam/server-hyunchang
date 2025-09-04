package com.simpleboard.board.board.application.converter;

import com.simpleboard.board.board.application.dto.request.PostDetailsQuery;
import com.simpleboard.board.board.application.dto.request.PostListQuery;
import com.simpleboard.board.board.application.dto.response.AuthorSummary;
import com.simpleboard.board.board.application.dto.response.PostDetailsQueryResult;
import com.simpleboard.board.board.application.dto.response.PostListQueryResult;
import com.simpleboard.board.board.application.query.PostDetailsCriteria;
import com.simpleboard.board.board.application.query.PostDetailsReadModel;
import com.simpleboard.board.board.application.query.PostListCriteria;
import com.simpleboard.board.board.application.query.PostListReadModel;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import java.util.Map;
import org.springframework.stereotype.Component;

/** <b>Post query service <-> repository 컨버터</b> */
@Component
public class PostQueryServiceRepoConverter {

  /**************************
   * Request converter
   * Service -> Repository
   **************************/

  public PostDetailsCriteria getCriteria(PostDetailsQuery query) {
    return PostDetailsCriteria.builder()
        .type(query.visitor().type())
        .vId(query.visitor().vId())
        .memberId(query.visitor().memberId())
        .postId(query.postId())
        .build();
  }

  public PostListCriteria getCriteria(PostListQuery query) {
    return PostListCriteria.builder()
        .boardId(query.boardId())
        .page(query.page())
        .size(query.size())
        .searchType(query.searchType())
        .keyword(query.keyword())
        .build();
  }

  /**************************
   * Response converter
   * Service <- Repository
   **************************/

  public PostDetailsQueryResult getQueryResult(PostDetailsReadModel readModel) {
    if (!readModel.postType().equals(PostTypeEnum.GUEST)) throw new IllegalArgumentException();
    return PostDetailsQueryResult.builder()
        .postId(readModel.postId())
        .postType(readModel.postType())
        .title(readModel.title())
        .content(readModel.content())
        .viewCount(readModel.viewCount())
        .isLiked(readModel.isLiked())
        .likeCount(readModel.likeCount())
        .updatedAt(readModel.updatedAt())
        .createdAt(readModel.createdAt())
        .hashTags(readModel.hashTags())
        .authorNickname(readModel.nickname())
        .build();
  }

  public PostDetailsQueryResult getQueryResult(
      PostDetailsReadModel readModel, AuthorSummary authorSummary) {
    if (!readModel.postType().equals(PostTypeEnum.MEMBER)) throw new IllegalArgumentException();
    return PostDetailsQueryResult.builder()
        .postId(readModel.postId())
        .postType(readModel.postType())
        .title(readModel.title())
        .content(readModel.content())
        .viewCount(readModel.viewCount())
        .isLiked(readModel.isLiked())
        .likeCount(readModel.likeCount())
        .updatedAt(readModel.updatedAt())
        .createdAt(readModel.createdAt())
        .hashTags(readModel.hashTags())
        .authorId(authorSummary.authorId())
        .authorNickname(authorSummary.nickname())
        .build();
  }

  public PostListQueryResult getQueryResult(
      Long boardId, String boardName, PostListReadModel readModel, Map<Long, String> nicknameMap) {
    return PostListQueryResult.builder()
        .boardId(boardId)
        .boardName(boardName)
        .totalPosts(readModel.totalPosts())
        .totalComments(readModel.totalComments())
        .page(readModel.page())
        .size(readModel.size())
        .posts(
            readModel.posts().stream()
                .map(
                    modelSummary -> {
                      PostListQueryResult.PostSummary.PostSummaryBuilder builder =
                          PostListQueryResult.PostSummary.builder()
                              .postId(modelSummary.postId())
                              .title(modelSummary.title())
                              .createdAt(modelSummary.createdAt())
                              .views(modelSummary.views())
                              .commentCount(modelSummary.commentCount())
                              .likeCount(modelSummary.likeCount());
                      if (modelSummary.postType().equals(PostTypeEnum.MEMBER))
                        builder.nickname(nicknameMap.get(modelSummary.authorId()));
                      else if (modelSummary.postType().equals(PostTypeEnum.GUEST))
                        builder.nickname(modelSummary.nickname());
                      else throw new IllegalArgumentException();

                      return builder.build();
                    })
                .toList())
        .build();
  }
}
