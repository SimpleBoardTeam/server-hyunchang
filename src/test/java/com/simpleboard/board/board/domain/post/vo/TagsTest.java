package com.simpleboard.board.board.domain.post.vo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.simpleboard.board.board.domain.post.entity.HashTag;
import com.simpleboard.board.board.domain.post.exception.HashTagsSizeException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <b>Tags VO 단위 테스트 클래스</b>
 *
 * <p>TagsVO를 통한 HashTag 생성, 파싱 테스트
 */
class TagsTest {

  @Test
  @DisplayName("HashTag 생성 테스트")
  void createTags() {
    String hashTagString = "hashTag";

    TagsVO tags = TagsVO.createTags(hashTagString);

    assertNotNull(tags);
    assertThat(tags.tags().size()).isEqualTo(1);
    assertThat(tags.tags().getFirst().getTag()).isEqualTo(hashTagString);
  }

  @Test
  @DisplayName("HashTag 파싱 테스트")
  void createTags_Parsing_Test() {
    List<String> tagList = List.of("tag1", "tag2", "tag3", "tag4");

    String hashTagString =
        tagList.get(0) + "   " + tagList.get(1) + "#" + tagList.get(2) + "  ," + tagList.get(3);

    TagsVO tags = TagsVO.createTags(hashTagString);

    assertNotNull(tags);
    assertThat(tags.tags()).extracting(HashTag::getTag).containsExactlyElementsOf(tagList);
  }

  @Test
  @DisplayName("HashTag 파싱 테스트2")
  void createTags_Parsing_Test2() {
    List<String> tagList = List.of("tag1", "tag2", "tag3", "tag4");

    String hashTagString =
        tagList.get(0)
            + " , ,"
            + tagList.get(1)
            + " # "
            + tagList.get(2)
            + " #  ,"
            + tagList.get(3);

    TagsVO tags = TagsVO.createTags(hashTagString);

    assertNotNull(tags);
    assertThat(tags.tags()).extracting(HashTag::getTag).containsExactlyElementsOf(tagList);
  }

  @Test
  @DisplayName("HashTag 중복 방지 테스트")
  void createTags_Prevent_Duplicate_Test() {
    List<String> tagList = List.of("tag1", "tag2", "tag3");

    String hashTagString =
        tagList.get(0) + "   " + tagList.get(1) + "#" + tagList.get(2) + ", " + tagList.get(2);
    TagsVO tags = TagsVO.createTags(hashTagString);

    assertNotNull(tags);
    assertThat(tags.tags()).extracting(HashTag::getTag).containsExactlyElementsOf(tagList);
  }

  @Test
  @DisplayName("Tags 개수 초과 실패 테스트")
  void tags_Size_Exceed_Fail_Test() {
    String hashTagString = "t1,t2,t3,t4,t5,t6";

    assertThrows(HashTagsSizeException.class, () -> TagsVO.createTags(hashTagString));
  }
}
