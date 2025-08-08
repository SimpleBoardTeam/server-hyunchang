package com.simpleboard.board.board.infrastructure.jpa.repository;

import com.simpleboard.board.board.domain.post.entity.HashTag;
import com.simpleboard.board.board.domain.post.entity.Post;
import com.simpleboard.board.board.domain.post.repository.PostCommandRepository;
import com.simpleboard.board.board.infrastructure.jpa.converter.PostConverter;
import com.simpleboard.board.board.infrastructure.jpa.entity.HashTagEntity;
import com.simpleboard.board.board.infrastructure.jpa.entity.PostEntity;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

/**
 * <b>Post B.C Command Repository 구현체</b>
 *
 * <p>Domain entity <-> JPA entity 간의 변환을 수행
 *
 * <p>HashTag의 조회 및 생성 담당
 */
@Component
@AllArgsConstructor
public class PostCommandRepositoryImpl implements PostCommandRepository {

  private final PostEntityRepository postRepository;
  private final HashTagEntityRepository hashTagRepository;
  private final PostConverter postConverter;

  /**
   * <b>Post의 조회를 수행하는 메서드</b>
   *
   * <p>id값으로 JPA entity를 조회,
   *
   * <p>domain entity로 변환하여 반환
   *
   * @param id Post id
   * @return domain entity
   */
  @Override
  public Optional<Post> findPostById(Long id) {
    Optional<PostEntity> optionalPost = postRepository.findById(id);
    if (optionalPost.isEmpty()) return Optional.empty();
    PostEntity post = optionalPost.get();
    return Optional.of(postConverter.toDomainEntity(post));
  }

  /**
   * <b>Post의 저장을 수행하는 메서드</b>
   *
   * <p>Domain entity -> JPA entity 변환을 수행
   *
   * <p>HashTag entity의 변환을 명령
   *
   * <p>JPA entity를 저장 후 반환값을 다시 변환 후 반환
   *
   * @param post domain entity
   * @return domain entity
   */
  @Override
  public Post savePost(Post post) {
    PostEntity entity = postConverter.toJpaEntity(post);
    List<HashTagEntity> list =
        post.getTags().tags().stream().map(this::getOrCreateHashTag).toList();
    entity.getTags().setTags(list);
    entity.getLikes().setPost(entity);
    PostEntity save = postRepository.save(entity);
    return postConverter.toDomainEntity(save);
  }

  /**
   * <b>HashTag 변환 메서드</b>
   *
   * <p>HashTag의 태그값이 repository에 존재하는지 확인 후
   *
   * <p>존재시 조회, 미존재시 생성
   *
   * <p>JPA entity로 변환하여 반환
   *
   * @param tag Domain entity
   * @return JPA entity
   */
  private HashTagEntity getOrCreateHashTag(HashTag tag) {
    return hashTagRepository
        .findByTag(tag.getTag())
        .orElseGet(
            () -> {
              try {
                return hashTagRepository.save(HashTagEntity.builder().tag(tag.getTag()).build());
              } catch (DataIntegrityViolationException e) {
                return hashTagRepository.findByTag(tag.getTag()).orElseThrow();
              }
            });
  }
}
