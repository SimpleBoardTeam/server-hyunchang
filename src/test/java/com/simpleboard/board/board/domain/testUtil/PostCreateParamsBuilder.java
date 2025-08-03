package com.simpleboard.board.board.domain.testUtil;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import com.simpleboard.board.board.domain.post.dto.CreateParams;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;

/**
 * <b>Test utility 클래스</b>
 *
 * <p>Post의 CreateParams Builder 클래스
 */
public class PostCreateParamsBuilder {
  // 기본값
  private Long boardId = 1L;
  private String boardName = "testboard";
  private String title = "title1";
  private String content = "content";
  private String hashTags = "";
  private PostTypeEnum type = PostTypeEnum.GUEST;
  private Long authorId = null;
  private String nickname = "nickname";
  private String password = "pw";

  private PostCreateParamsBuilder() {}

  /**
   * <b>CreateParams Build 클래스</b>
   *
   * <p>Visitor에 맞게 MemberPost/GuestPost 를 생성하는 CreateParams 생성
   *
   * <p>주입하고 싶은 필드값들을 주입 후 build() 사용
   *
   * @since 1.0
   */
  public static PostCreateParamsBuilder builder(Visitor visitor) {

    if (visitor.type() == VisitorType.GUEST) return new PostCreateParamsBuilder().guest(visitor);
    else if (visitor.type() == VisitorType.MEMBER)
      return new PostCreateParamsBuilder().member(visitor);
    return null;
  }

  public PostCreateParamsBuilder guest(Visitor visitor) {
    this.type = PostTypeEnum.GUEST;
    this.authorId = null;
    this.nickname = "nickname";
    this.password = "pw";
    return this;
  }

  public PostCreateParamsBuilder member(Visitor visitor) {
    this.type = PostTypeEnum.MEMBER;
    this.authorId = visitor.memberId();
    this.nickname = "";
    this.password = "";
    return this;
  }

  // 필드 개별 설정
  public PostCreateParamsBuilder boardId(Long boardId) {
    this.boardId = boardId;
    return this;
  }

  public PostCreateParamsBuilder boardName(String boardName) {
    this.boardName = boardName;
    return this;
  }

  public PostCreateParamsBuilder title(String title) {
    this.title = title;
    return this;
  }

  public PostCreateParamsBuilder content(String content) {
    this.content = content;
    return this;
  }

  public PostCreateParamsBuilder hashTags(String hashTags) {
    this.hashTags = hashTags;
    return this;
  }

  public PostCreateParamsBuilder type(PostTypeEnum type) {
    this.type = type;
    return this;
  }

  public PostCreateParamsBuilder nickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public PostCreateParamsBuilder password(String password) {
    this.password = password;
    return this;
  }

  // 해시태그 추가식
  public PostCreateParamsBuilder appendHashTags(String append) {
    if (this.hashTags == null || this.hashTags.isBlank()) this.hashTags = append;
    else this.hashTags += "," + append;
    return this;
  }

  public CreateParams build() {
    return new CreateParams(
        boardId, boardName, title, content, hashTags, type, authorId, nickname, password);
  }
}
