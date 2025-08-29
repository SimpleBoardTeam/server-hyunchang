package com.simpleboard.board.board.infrastructure.mybatis.repository;

import com.simpleboard.board.board.application.query.*;
import com.simpleboard.board.board.domain.post.exception.PostNotFoundException;
import com.simpleboard.board.board.infrastructure.mybatis.converter.PostQueryConverter;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostDetailsData;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostListCondition;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostQueryMapper;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostSummaryData;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

  private final PostQueryMapper postQueryMapper;

  @Override
  public PostDetailsReadModel getPostDetails(PostDetailsCriteria criteria) {
    PostDetailsData postDetails =
        postQueryMapper.getPostDetails(PostQueryConverter.postDetailsCondition(criteria));
    if (postDetails == null) throw new PostNotFoundException();
    List<String> postHashTags = postQueryMapper.getPostHashTags(criteria.postId());

    return PostQueryConverter.getPostDetailsReadModel(postDetails, postHashTags);
  }

  @Override
  public PostListReadModel getPostList(PostListCriteria criteria) {
    PostListCondition condition = PostQueryConverter.postListCondition(criteria);
    List<PostSummaryData> rows = postQueryMapper.getPostList(condition);

    long totalPosts = postQueryMapper.countPosts(condition);
    long totalComments = postQueryMapper.countCommentsOfBoard(criteria.boardId());

    return PostQueryConverter.getPostListReadModel(criteria, rows, totalPosts, totalComments);
  }
}
