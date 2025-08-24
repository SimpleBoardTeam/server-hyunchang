package com.simpleboard.board.board.presentation.web;

import com.simpleboard.board.board.application.dto.request.PostCreateCommand;
import com.simpleboard.board.board.application.dto.request.PostDeleteCommand;
import com.simpleboard.board.board.application.dto.request.PostEditCommand;
import com.simpleboard.board.board.application.dto.response.PostCreateResult;
import com.simpleboard.board.board.application.dto.response.PostEditResult;
import com.simpleboard.board.board.application.dto.response.PostToggleLikeResult;
import com.simpleboard.board.board.application.service.PostCommandService;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.presentation.converter.PostFormCommandConverter;
import com.simpleboard.board.board.presentation.dto.request.PostCreateForm;
import com.simpleboard.board.board.presentation.dto.request.PostDeleteForm;
import com.simpleboard.board.board.presentation.dto.request.PostEditForm;
import com.simpleboard.board.board.presentation.util.VisitorProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <b>Post Aggregate Command Controller</b>
 *
 * <p>Post aggregate에 대한 CUD 요청을 받아 application 계층에 대한 명령을 보낸다.
 */
@Tag(name = "Auth - Post API", description = "게시글 CUD API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostCommandController {

  private final VisitorProvider visitorProvider;
  private final PostCommandService postCommandService;
  private final PostFormCommandConverter converter;

  @Operation(summary = "게시글 생성 API", description = "회원/비회원의 게시글을 생성합니다.")
  @PostMapping
  public ResponseEntity<Void> createPost(@RequestBody @Valid PostCreateForm form) {
    Visitor visitor = visitorProvider.getVisitor();
    PostCreateCommand command = converter.toCreateCommand(form, visitor);
    PostCreateResult result = postCommandService.createPost(visitor, command);
    return ResponseEntity.created(URI.create("/posts/" + result.postId())).build();
  }

  @Operation(summary = "게시글 수정 API", description = "회원/비회원의 게시글을 수정합니다.")
  @PutMapping("/{postId}")
  public ResponseEntity<Void> editPost(
      @PathVariable Long postId, @RequestBody @Valid PostEditForm form) {
    Visitor visitor = visitorProvider.getVisitor();
    PostEditCommand command = converter.toEditCommand(postId, form, visitor);
    PostEditResult result = postCommandService.editPost(visitor, command);
    return ResponseEntity.noContent().location(URI.create("/posts/" + result.postId())).build();
  }

  @Operation(summary = "게시글 삭제 API", description = "회원/비회원의 게시글을 삭제합니다.")
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePost(
      @PathVariable Long postId, @RequestBody(required = false) PostDeleteForm form) {
    Visitor visitor = visitorProvider.getVisitor();
    PostDeleteCommand command =
        converter.toDeleteCommand(
            postId, form == null ? PostDeleteForm.builder().build() : form, visitor);
    postCommandService.deletePost(visitor, command);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "게시글 좋아요 토글 API", description = "현재 방문자 기준으로 좋아요를 토글합니다.")
  @PostMapping("/{postId}/likes/toggle")
  public ResponseEntity<PostToggleLikeResult> toggleLike(@PathVariable Long postId) {
    Visitor visitor = visitorProvider.getVisitor();
    PostToggleLikeResult result = postCommandService.toggleLike(visitor, postId);
    return ResponseEntity.ok(result);
  }
}
