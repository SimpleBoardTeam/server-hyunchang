package com.simpleboard.board.board.domain.post.entity;

/**
 * <b>HashTag</b> Entity(non‑root)
 *
 * <p>Post의 해시태그 정보
 *
 * @domain entity
 * @since 1.0
 */
public class HashTag {
  String tag;

  public HashTag(String tag) {
    this.tag = tag.replaceAll(" ", "");
  }
}
