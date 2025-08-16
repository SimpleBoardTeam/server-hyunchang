package com.simpleboard.board.board.application.converter;

import com.simpleboard.board.board.application.dto.request.PostCreateCommand;
import com.simpleboard.board.board.application.dto.request.PostDeleteCommand;
import com.simpleboard.board.board.application.dto.request.PostEditCommand;
import com.simpleboard.board.board.domain.post.dto.PostCreateParams;
import com.simpleboard.board.board.domain.post.dto.PostDeleteParams;
import com.simpleboard.board.board.domain.post.dto.PostEditParams;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import org.springframework.stereotype.Component;

/**
 * <b>Post command -> params Converter</b>
 *
 * <p>Post의 command dto를 domain dto인 params 클래스로 변환
 */
@Component
public class PostCommandParamsConverter {

  public PostCreateParams createCommandToParams(PostCreateCommand command) {
    if (command.postType() == null) throw new IllegalArgumentException("Post type is null");
    if (command.postType().equals(PostTypeEnum.GUEST)) {
      return PostCreateParams.builder()
          .boardId(command.boardId())
          .title(command.title())
          .content(command.content())
          .type(command.postType())
          .hashTags(command.hashTags())
          .nickname(command.nickname())
          .password(command.password())
          .build();
    } else if (command.postType().equals(PostTypeEnum.MEMBER)) {
      return PostCreateParams.builder()
          .boardId(command.boardId())
          .title(command.title())
          .content(command.content())
          .type(command.postType())
          .hashTags(command.hashTags())
          .authorId(command.authorId())
          .build();
    }
    throw new IllegalArgumentException("Unknown post type: " + command.postType());
  }

  public PostEditParams editCommandToParams(PostEditCommand command) {
    if (command.postType() == null) throw new IllegalArgumentException("Post type is null");
    if (command.postType().equals(PostTypeEnum.GUEST)) {
      return PostEditParams.builder()
          .title(command.title())
          .content(command.content())
          .type(command.postType())
          .hashTags(command.hashTags())
          .password(command.password())
          .build();
    } else if (command.postType().equals(PostTypeEnum.MEMBER)) {
      return PostEditParams.builder()
          .title(command.title())
          .content(command.content())
          .type(command.postType())
          .hashTags(command.hashTags())
          .build();
    }
    throw new IllegalArgumentException("Unknown post type: " + command.postType());
  }

  public PostDeleteParams deleteCommandToParams(PostDeleteCommand command) {
    if (command.postType() == null) throw new IllegalArgumentException("Post type is null");
    if (command.postType().equals(PostTypeEnum.GUEST)) {
      return PostDeleteParams.builder().password(command.password()).build();
    } else if (command.postType().equals(PostTypeEnum.MEMBER)) {
      return PostDeleteParams.builder().build();
    }
    throw new IllegalArgumentException("Unknown post type: " + command.postType());
  }
}
