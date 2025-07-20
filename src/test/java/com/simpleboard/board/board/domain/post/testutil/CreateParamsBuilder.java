package com.simpleboard.board.board.domain.post.testutil;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import com.simpleboard.board.board.domain.post.dto.CreateParams;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;

/**
 * Test utility 클래스
 * Post의 CreateParams record Builder 클래스
 *
 */
public class CreateParamsBuilder {
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

  private CreateParamsBuilder() {}

  /**
   * <b>CreateParams Build 클래스</b>
   * <p>Visitor에 맞게 MemberPost/GuestPost 를 생성하는 CreateParams 생성</p>
   * <p>주입하고 싶은 필드값들을 주입 후 build() 사용</p>
   * @since 1.0
   */
  public static CreateParamsBuilder builder(Visitor visitor) {

    if (visitor.type() == VisitorType.GUEST) return new CreateParamsBuilder().guest(visitor);
    else if (visitor.type() == VisitorType.MEMBER) return new CreateParamsBuilder().member(visitor);
    return null;
  }

  public CreateParamsBuilder guest(Visitor visitor) {
    this.type = PostTypeEnum.GUEST;
    this.authorId = null;
    this.nickname = "nickname";
    this.password = "pw";
    return this;
  }

  public CreateParamsBuilder member(Visitor visitor) {
    this.type = PostTypeEnum.MEMBER;
    this.authorId = visitor.memberId();
    this.nickname = "";
    this.password = "";
    return this;
  }

  // 필드 개별 설정
  public CreateParamsBuilder boardId(Long boardId) {
    this.boardId = boardId;
    return this;
  }

  public CreateParamsBuilder boardName(String boardName) {
    this.boardName = boardName;
    return this;
  }

  public CreateParamsBuilder title(String title) {
    this.title = title;
    return this;
  }

  public CreateParamsBuilder content(String content) {
    this.content = content;
    return this;
  }

  public CreateParamsBuilder hashTags(String hashTags) {
    this.hashTags = hashTags;
    return this;
  }

  public CreateParamsBuilder type(PostTypeEnum type) {
    this.type = type;
    return this;
  }

  public CreateParamsBuilder nickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public CreateParamsBuilder password(String password) {
    this.password = password;
    return this;
  }

  // 해시태그 추가식
  public CreateParamsBuilder appendHashTags(String append) {
    if (this.hashTags == null || this.hashTags.isBlank()) this.hashTags = append;
    else this.hashTags += "," + append;
    return this;
  }

  public CreateParams build() {
    return new CreateParams(
        boardId, boardName, title, content, hashTags, type, authorId, nickname, password);
  }
}
