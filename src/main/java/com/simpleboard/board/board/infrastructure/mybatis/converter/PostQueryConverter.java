package com.simpleboard.board.board.infrastructure.mybatis.converter;

import com.simpleboard.board.board.application.query.PostDetailsCriteria;
import com.simpleboard.board.board.application.query.PostDetailsReadModel;
import com.simpleboard.board.board.application.query.PostListCriteria;
import com.simpleboard.board.board.application.query.PostListReadModel;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostDetailsCondition;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostDetailsData;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostListCondition;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostSummaryData;
import java.util.List;

/**
 * <b>Repository <-> Mapper converter</b>
 *
 * <p>Criteria -> Condition 으로 변환
 *
 * <p>Data -> ReadModel 으로 변환
 */
public class PostQueryConverter {

  /**************************
   * Request converter
   * Repository -> Mapper
   **************************/

  public static PostDetailsCondition postDetailsCondition(PostDetailsCriteria criteria) {
    return PostDetailsCondition.builder()
        .visitorType(criteria.type())
        .vid(criteria.vId())
        .memberId(criteria.memberId())
        .postId(criteria.postId())
        .build();
  }

  public static PostListCondition postListCondition(PostListCriteria criteria) {
    return PostListCondition.builder()
        .boardId(criteria.boardId())
        .limit(criteria.size())
        .offset(criteria.page() * criteria.size())
        .searchType(criteria.searchType())
        .keyword(criteria.keyword())
        .build();
  }

  /**************************
   * Request converter
   * Repository -> Mapper
   **************************/

  public static PostDetailsReadModel getPostDetailsReadModel(
      PostDetailsData info, List<String> hashTags) {
    return PostDetailsReadModel.builder()
        .postId(info.postId())
        .postType(info.postType())
        .title(info.title())
        .content(info.content())
        .likeCount(info.likeCount())
        .isLiked(info.isLiked())
        .viewCount(info.viewCount())
        .hashTags(hashTags)
        .createdAt(info.createdAt())
        .updatedAt(info.updatedAt())
        .build();
  }

  public static PostListReadModel getPostListReadModel(
      PostListCriteria criteria, List<PostSummaryData> rows, long totalPosts, long totalComments) {
    long startIdx = criteria.page() * criteria.size();
    long endIdx = startIdx + rows.size();
    long newPage = (endIdx + 1) / criteria.size();
    return PostListReadModel.builder()
        .totalPosts(totalPosts)
        .totalComments(totalComments)
        .page((int) newPage)
        .size(criteria.size())
        .posts(
            rows.stream()
                .map(
                    (row) ->
                        PostListReadModel.PostSummary.builder()
                            .postId(row.postId())
                            .title(row.title())
                            .postType(row.postType())
                            .authorId(row.authorId())
                            .nickname(row.nickname())
                            .createdAt(row.createdAt())
                            .views(row.views())
                            .commentCount(row.commentCount())
                            .likeCount(row.likeCount())
                            .build())
                .toList())
        .build();
  }
}
