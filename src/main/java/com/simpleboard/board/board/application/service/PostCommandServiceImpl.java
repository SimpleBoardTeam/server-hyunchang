package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.converter.PostCommandParamsConverter;
import com.simpleboard.board.board.application.converter.PostInfoResultConverter;
import com.simpleboard.board.board.application.dto.request.PostCreateCommand;
import com.simpleboard.board.board.application.dto.request.PostDeleteCommand;
import com.simpleboard.board.board.application.dto.request.PostEditCommand;
import com.simpleboard.board.board.application.dto.response.PostCreateResult;
import com.simpleboard.board.board.application.dto.response.PostEditResult;
import com.simpleboard.board.board.application.dto.response.PostToggleLikeResult;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.dto.LikeInfo;
import com.simpleboard.board.board.domain.post.dto.PostCreateParams;
import com.simpleboard.board.board.domain.post.dto.PostDeleteParams;
import com.simpleboard.board.board.domain.post.dto.PostEditParams;
import com.simpleboard.board.board.domain.post.entity.Post;
import com.simpleboard.board.board.domain.post.exception.PostNotFoundException;
import com.simpleboard.board.board.domain.post.repository.PostCommandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

  private final PostCommandParamsConverter postCommandParamsConverter;
  private final PostInfoResultConverter postInfoResultConverter;
  private final PostCommandRepository postCommandRepository;

  @Override
  public PostCreateResult createPost(Visitor visitor, PostCreateCommand command) {
    PostCreateParams params = postCommandParamsConverter.createCommandToParams(command);
    Post post = Post.write(params);

    Post saved = postCommandRepository.savePost(post);

    return new PostCreateResult(saved.getId());
  }

  @Override
  public PostEditResult editPost(Visitor visitor, PostEditCommand command) {
    Long postId = command.postId();
    PostEditParams params = postCommandParamsConverter.editCommandToParams(command);

    Post post =
        postCommandRepository.findPostById(postId).orElseThrow(() -> new PostNotFoundException());
    post.editPost(visitor, params);

    Post saved = postCommandRepository.savePost(post);

    return new PostEditResult(saved.getId());
  }

  @Override
  public void deletePost(Visitor visitor, PostDeleteCommand command) {
    Long postId = command.postId();
    PostDeleteParams params = postCommandParamsConverter.deleteCommandToParams(command);

    Post post =
        postCommandRepository.findPostById(postId).orElseThrow(() -> new PostNotFoundException());
    post.deletePost(visitor, params);
    Post saved = postCommandRepository.savePost(post);
  }

  @Override
  @Transactional
  public PostToggleLikeResult toggleLike(Visitor visitor, Long postId) {
    Post post =
        postCommandRepository.findPostById(postId).orElseThrow(() -> new PostNotFoundException());
    LikeInfo likeInfo = post.toggleLike(visitor);
    postCommandRepository.savePost(post);
    return postInfoResultConverter.toggleLikeInfoToResult(likeInfo);
  }
}
