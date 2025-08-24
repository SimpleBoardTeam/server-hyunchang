package com.simpleboard.board.board.application.testutil;

import com.simpleboard.board.board.domain.post.entity.GuestPost;
import com.simpleboard.board.board.domain.post.entity.MemberPost;
import com.simpleboard.board.board.domain.post.entity.Post;
import com.simpleboard.board.board.domain.post.repository.PostCommandRepository;
import com.simpleboard.board.board.infrastructure.jpa.converter.PostConverter;
import com.simpleboard.board.board.infrastructure.jpa.entity.GuestPostEntity;
import com.simpleboard.board.board.infrastructure.jpa.entity.MemberPostEntity;
import com.simpleboard.board.board.infrastructure.jpa.entity.PostEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.test.util.ReflectionTestUtils;

public class PostCommandMockRepository implements PostCommandRepository {

  private PostConverter postConverter = new PostConverter();
  private Map<Long, GuestPostEntity> guestDb = new HashMap<>();
  private Map<Long, MemberPostEntity> memberDb = new HashMap<>();
  private Long nextId = 1L;

  @Override
  public Optional<Post> findPostById(Long id) {
    PostEntity entity = guestDb.get(id);
    if (entity == null) {
      entity = memberDb.get(id);
    }
    if (entity == null) {
      return Optional.empty();
    }
    return Optional.of(postConverter.toDomainEntity(entity));
  }

  @Override
  public Post savePost(Post post) {
    PostEntity jpaEntity = postConverter.toJpaEntity(post);
    if (jpaEntity.getId() == null) {
      Long newId = getNextId();
      ReflectionTestUtils.setField(jpaEntity, "id", newId);
    }
    if (post instanceof GuestPost) {
      guestDb.put(jpaEntity.getId(), (GuestPostEntity) jpaEntity);
    } else if (post instanceof MemberPost) {
      memberDb.put(jpaEntity.getId(), (MemberPostEntity) jpaEntity);
    }
    return findPostById(jpaEntity.getId()).orElseThrow();
  }

  private Long getNextId() {
    return nextId++;
  }
}
