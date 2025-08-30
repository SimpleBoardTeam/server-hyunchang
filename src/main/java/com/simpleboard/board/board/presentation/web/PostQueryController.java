package com.simpleboard.board.board.presentation.web;

import com.simpleboard.board.board.application.dto.response.PostDetailsQueryResult;
import com.simpleboard.board.board.application.dto.response.PostListQueryResult;
import com.simpleboard.board.board.application.service.PostQueryService;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.presentation.converter.PostFormQueryConverter;
import com.simpleboard.board.board.presentation.converter.PostResultResponseConverter;
import com.simpleboard.board.board.presentation.dto.request.PostListQueryForm;
import com.simpleboard.board.board.presentation.dto.response.PostDetailsResponse;
import com.simpleboard.board.board.presentation.dto.response.PostListResponse;
import com.simpleboard.board.board.presentation.util.VisitorProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <b>Post Aggregate Query Controller</b>
 *
 * <p>Post aggregate에 대한 Read 요청을 받아 application 계층에 조회 명령을 보낸다.
 */
@Tag(name = "Board - Post Query API", description = "게시글 Read API")
@RestController
@RequiredArgsConstructor
@RequestMapping
public class PostQueryController {

  private final PostQueryService postQueryService;
  private final VisitorProvider visitorProvider;
  private final PostFormQueryConverter requestConverter;
  private final PostResultResponseConverter responseConverter;

  @Operation(summary = "게시글 상세조회 API", description = "게시글 상세조회를 요청합니다.")
  @GetMapping("/posts/{postId}")
  ResponseEntity<PostDetailsResponse> getPostDetails(@PathVariable Long postId) {
    Visitor visitor = visitorProvider.getVisitor();
    PostDetailsQueryResult result =
        postQueryService.getPostDetails(requestConverter.toQuery(visitor, postId));

    return ResponseEntity.ok(responseConverter.toResponse(result));
  }

  @Operation(
      summary = "게시글 목록 조회 API",
      description =
          "게시글 목록 조회를 요청합니다.\n" + "GET /boards/1/posts?page=0&size=10&searchType=TITLE&keyword=JPA")
  @GetMapping("/boards/{boardId}/posts")
  ResponseEntity<PostListResponse> getPostList(
      @PathVariable Long boardId, @ModelAttribute PostListQueryForm form) {
    PostListQueryResult result =
        postQueryService.getPostList(requestConverter.toQuery(form, boardId));

    return ResponseEntity.ok(responseConverter.toResponse(result));
  }
}
