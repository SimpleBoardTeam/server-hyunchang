package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.dto.request.PostCreateCommand;
import com.simpleboard.board.board.application.dto.request.PostDeleteCommand;
import com.simpleboard.board.board.application.dto.request.PostEditCommand;
import com.simpleboard.board.board.application.dto.response.PostCreateResult;
import com.simpleboard.board.board.application.dto.response.PostEditResult;
import com.simpleboard.board.board.application.dto.response.PostToggleLikeResult;
import com.simpleboard.board.board.domain.common.vo.Visitor;

/**
 * Post Command 유스케이스.
 *
 * @domain application-service
 * @transactional
 */
public interface PostCommandService {

  PostCreateResult createPost(Visitor visitor, PostCreateCommand command);

  PostEditResult editPost(Visitor visitor, PostEditCommand command);

  void deletePost(Visitor visitor, PostDeleteCommand command);

  PostToggleLikeResult toggleLike(Visitor visitor, Long postId);
}
