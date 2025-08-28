package com.simpleboard.board.board.domain.comment.repository;

import com.simpleboard.board.board.domain.comment.entity.Comment;
import java.util.Optional;

/**
 * Comment 저장소 포트.
 *
 * <p>Comment에 대한 저장, 조회 수행
 *
 * @domain repository-port
 */
public interface CommentCommandRepository {
  Comment save(Comment comment);

  Optional<Comment> findById(Long id);
}
