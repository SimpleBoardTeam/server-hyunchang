package com.simpleboard.board.board.infrastructure.mybatis.repository;

import com.simpleboard.board.board.application.query.PostDetailsCriteria;
import com.simpleboard.board.board.application.query.PostDetailsReadModel;
import com.simpleboard.board.board.application.query.PostQueryRepository;
import com.simpleboard.board.board.domain.post.exception.PostNotFoundException;
import com.simpleboard.board.board.infrastructure.mybatis.converter.PostQueryConverter;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostDetailsData;
import com.simpleboard.board.board.infrastructure.mybatis.mapper.PostQueryMapper;
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

  //    @Override
  //    public PostListQueryResult getPostList(PostListQuery query) {
  //        PostListCondition condition = QueryConverter.postListCondition(query);
  //        List<PostListRow> rows = postQueryMapper.getPostList(condition);
  //
  //        long totalPosts = postQueryMapper.countPosts(condition);
  //        long totalComments = postQueryMapper.countCommentsOfBoard(query.boardId());
  //
  //
  //    }
}
