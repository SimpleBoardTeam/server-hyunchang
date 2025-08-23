package com.simpleboard.board.board.infrastructure.mybatis.converter;

import com.simpleboard.board.board.application.query.PostDetailsCriteria;
import com.simpleboard.board.board.application.query.PostDetailsReadModel;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostDetailsCondition;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostDetailsData;
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
}
