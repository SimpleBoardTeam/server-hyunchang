package com.simpleboard.board.board.presentation.converter;

import com.simpleboard.board.board.application.dto.request.PostDetailsQuery;
import com.simpleboard.board.board.application.dto.request.PostListQuery;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.presentation.dto.request.PostListQueryForm;
import org.springframework.stereotype.Component;

/**
 * <b>Post QueryForm to Query 컨버터 클래스</b>
 *
 * <p>QueryForm DTO -> Query DTO로 변환을 담당한다.
 *
 * <p>Presentation -> application
 */
@Component
public class PostFormQueryConverter {

  public PostDetailsQuery toQuery(Visitor visitor, Long postId) {
    return PostDetailsQuery.builder().visitor(visitor).postId(postId).build();
  }

  public PostListQuery toQuery(PostListQueryForm form, Long boardId) {
    int page = form.page() == null ? 0 : form.page();
    int size = (form.size() == null || (form.size() != 10 && form.size() != 20)) ? 10 : form.size();

    return PostListQuery.builder()
        .boardId(boardId)
        .page(page)
        .size(size)
        .keyword(form.keyword())
        .searchType(form.searchType().toString())
        .build();
  }
}
