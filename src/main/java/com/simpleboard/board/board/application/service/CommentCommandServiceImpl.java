package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.converter.CommentCommandParamsConverter;
import com.simpleboard.board.board.application.converter.CommentResultConverter;
import com.simpleboard.board.board.application.dto.request.CommentCreateCommand;
import com.simpleboard.board.board.application.dto.request.CommentDeleteCommand;
import com.simpleboard.board.board.application.dto.response.CommentDetailResult;
import com.simpleboard.board.board.application.exception.CommentNotFoundException;
import com.simpleboard.board.board.application.exception.PostNotExistsException;
import com.simpleboard.board.board.domain.comment.entity.Comment;
import com.simpleboard.board.board.domain.comment.entity.GuestComment;
import com.simpleboard.board.board.domain.comment.entity.MemberComment;
import com.simpleboard.board.board.domain.comment.repository.CommentCommandRepository;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.post.repository.PostCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Comment 생성, 삭제 유스케이스.
 *
 * @domain application-service
 * @transactional
 */
@Service
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

  private final PostCommandRepository postCommandRepository;
  private final CommentCommandParamsConverter commandParamsConverter;
  private final CommentResultConverter commentResultConverter;
  private final CommentCommandRepository commentCommandRepository;
  private final MemberFetchService memberFetchService;

  public CommentDetailResult createComment(Visitor visitor, CommentCreateCommand command) {
    Long postId = command.postId();
    if (postCommandRepository.findPostById(postId).isEmpty()) throw new PostNotExistsException();
    Comment comment = Comment.write(commandParamsConverter.toCreateParams(command));
    Comment save = commentCommandRepository.save(comment);

    // TODO: CommentCreatedEvent

    if (save instanceof MemberComment) {
      String nickname = memberFetchService.fetchNickname(((MemberComment) save).getWriterId());
      return commentResultConverter.toCommentDetailResult(save, nickname);
    } else if (save instanceof GuestComment) {
      return commentResultConverter.toCommentDetailResult(save, null);
    }

    return null;
  }

  public CommentDetailResult deleteComment(Visitor visitor, CommentDeleteCommand command) {
    Long commentId = command.commentId();
    Comment comment =
        commentCommandRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    // 검증
    // 삭제
    comment.deleteComment(visitor, commandParamsConverter.toDeleteParams(command));
    Comment saved = commentCommandRepository.save(comment);
    // 삭제 이벤트 발행
    // TODO: CommentDeletedEvent
    // 삭제 결과 반환
    return commentResultConverter.toCommentDetailResult(saved, null);
  }
}
