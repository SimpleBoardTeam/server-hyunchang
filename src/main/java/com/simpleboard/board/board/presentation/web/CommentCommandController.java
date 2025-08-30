package com.simpleboard.board.board.presentation.web;

import com.simpleboard.board.board.application.dto.request.CommentCreateCommand;
import com.simpleboard.board.board.application.dto.request.CommentDeleteCommand;
import com.simpleboard.board.board.application.dto.response.CommentDetailResult;
import com.simpleboard.board.board.application.service.CommentCommandService;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.presentation.converter.CommentFormCommandConverter;
import com.simpleboard.board.board.presentation.converter.CommentResultResponseConverter;
import com.simpleboard.board.board.presentation.dto.request.CommentCreateForm;
import com.simpleboard.board.board.presentation.dto.request.CommentDeleteForm;
import com.simpleboard.board.board.presentation.dto.response.CommentDetailResponse;
import com.simpleboard.board.board.presentation.util.VisitorProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth - Comment API", description = "댓글 CUD API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentCommandController {

  private final CommentCommandService commentCommandService;
  private final CommentFormCommandConverter requestConverter;
  private final CommentResultResponseConverter responseConverter;

  private final VisitorProvider visitorProvider;

  @Operation(summary = "댓글 생성 API", description = "댓글 생성 후 생성한 댓글 단건 조회 결과를 반환합니다.")
  @PostMapping
  public ResponseEntity<CommentDetailResponse> createComment(
      @RequestBody @Valid CommentCreateForm form) {
    Visitor visitor = visitorProvider.getVisitor();
    CommentCreateCommand command = requestConverter.toCommentCreateCommand(form, visitor);
    CommentDetailResult result = commentCommandService.createComment(visitor, command);
    return ResponseEntity.ok(responseConverter.toResponse(result));
  }

  @Operation(summary = "댓글 생성 API", description = "댓글을 soft delete 후 단건 조회 결과를 반환합니다.")
  @DeleteMapping("/{commentId}")
  public ResponseEntity<CommentDetailResponse> deleteComment(
      @PathVariable Long commentId, @RequestBody(required = false) @Valid CommentDeleteForm form) {
    Visitor visitor = visitorProvider.getVisitor();
    CommentDeleteCommand command =
        requestConverter.toCommentDeleteCommand(commentId, form, visitor);
    CommentDetailResult result = commentCommandService.deleteComment(visitor, command);
    return ResponseEntity.ok(responseConverter.toResponse(result));
  }
}
