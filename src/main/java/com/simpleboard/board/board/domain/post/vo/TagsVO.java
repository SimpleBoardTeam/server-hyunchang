package com.simpleboard.board.board.domain.post.vo;

import com.simpleboard.board.board.domain.post.entity.HashTag;
import com.simpleboard.board.board.domain.post.exception.HashTagsSizeException;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;

/**
 * <b>Tags</b> Value Object.
 *
 * <p>Post의 해시태그 관련 책임 담당 VO
 *
 * @domain value-object
 * @since 1.0
 */
@Builder
public record TagsVO(List<HashTag> tags) {

  /**
   * <b>TagsVO 생성 메서드</b>
   *
   * <p>하나의 String으로 된 hashtag를 받아 파싱, HastTag 엔티티를 생성한 뒤 TagsVO 객체에 삽입해 반환
   *
   * @since 1.0
   */
  public static TagsVO createTags(String hashTags) {
    List<HashTag> newTags =
        Arrays.stream(hashTags.split("[#, ]"))
            .map(HashTag::new)
            .filter(hashTag -> !hashTag.getTag().isBlank())
            .distinct()
            .toList();
    if (newTags.size() > 5) throw new HashTagsSizeException("");
    return new TagsVO(newTags);
  }
}
