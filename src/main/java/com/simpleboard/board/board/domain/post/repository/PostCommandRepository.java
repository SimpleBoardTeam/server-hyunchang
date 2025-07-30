package com.simpleboard.board.board.domain.post.repository;

import com.simpleboard.board.board.domain.post.entity.Post;
import java.util.Optional;

public interface PostCommandRepository {
  Optional<Post> findPostById(Long id);

  Post savePost(Post post);
}
