package com.simpleboard.board.board.presentation.converter;

import com.simpleboard.board.board.application.dto.request.PostCreateCommand;
import com.simpleboard.board.board.application.dto.request.PostDeleteCommand;
import com.simpleboard.board.board.application.dto.request.PostEditCommand;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import com.simpleboard.board.board.domain.post.vo.PostTypeEnum;
import com.simpleboard.board.board.presentation.dto.request.PostCreateForm;
import com.simpleboard.board.board.presentation.dto.request.PostDeleteForm;
import com.simpleboard.board.board.presentation.dto.request.PostEditForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * <b>Post Form to Command 컨버터 클래스</b>
 *
 * <p>Form DTO -> Command DTO로 변환을 담당한다.
 */
@Component
@RequiredArgsConstructor
public class PostFormCommandConverter {

  public PostCreateCommand toCreateCommand(PostCreateForm form, Visitor visitor) {
    PostCreateCommand.PostCreateCommandBuilder builder =
        PostCreateCommand.builder()
            .boardId(form.boardId())
            .title(form.title())
            .content(form.content())
            .hashTags(form.hashtags());
    if (visitor.type().equals(VisitorType.MEMBER)) {
      return builder.postType(PostTypeEnum.MEMBER).authorId(visitor.memberId()).build();
    } else if (visitor.type().equals(VisitorType.GUEST)) {
      return builder
          .postType(PostTypeEnum.GUEST)
          .nickname(form.nickname())
          .password(form.password())
          .build();
    }
    throw new IllegalArgumentException("Post type not supported");
  }

  public PostEditCommand toEditCommand(Long postId, PostEditForm form, Visitor visitor) {
    PostEditCommand.PostEditCommandBuilder builder =
        PostEditCommand.builder()
            .postId(postId)
            .title(form.title())
            .content(form.content())
            .hashTags(form.hashTags());

    if (visitor.type().equals(VisitorType.MEMBER)) {
      return builder.postType(PostTypeEnum.MEMBER).build();
    } else if (visitor.type().equals(VisitorType.GUEST)) {
      return builder.postType(PostTypeEnum.GUEST).password(form.password()).build();
    }
    throw new IllegalArgumentException("Post type not supported");
  }

  public PostDeleteCommand toDeleteCommand(Long postId, PostDeleteForm form, Visitor visitor) {
    PostDeleteCommand.PostDeleteCommandBuilder builder = PostDeleteCommand.builder().postId(postId);
    if (visitor.type().equals(VisitorType.MEMBER)) {
      return builder.postType(PostTypeEnum.MEMBER).build();
    } else if (visitor.type().equals(VisitorType.GUEST)) {
      return builder.postType(PostTypeEnum.GUEST).password(form.password()).build();
    }
    throw new IllegalArgumentException("Post type not supported");
  }
}
