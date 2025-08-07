package com.simpleboard.board.board.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * <b>HashTag</b> Entity(non‑root)
 *
 * <p>Post의 해시태그 정보
 *
 * @domain entity
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor
public class HashTag {
  private Long id;
  private String tag;

  public HashTag(String tag) {
    this.tag = tag.replaceAll(" ", "");
  }

  @Override
  public boolean equals(Object o) {
    return tag.equals(((HashTag) o).tag);
  }

  @Override
  public int hashCode() {
    return tag.hashCode();
  }
}
