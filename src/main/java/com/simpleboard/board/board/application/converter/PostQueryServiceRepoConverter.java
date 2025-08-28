package com.simpleboard.board.board.application.converter;

import com.simpleboard.board.board.application.dto.request.PostDetailsQuery;
import com.simpleboard.board.board.application.dto.response.AuthorSummary;
import com.simpleboard.board.board.application.dto.response.PostDetailsQueryResult;
import com.simpleboard.board.board.application.query.PostDetailsCriteria;
import com.simpleboard.board.board.application.query.PostDetailsReadModel;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
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
}
