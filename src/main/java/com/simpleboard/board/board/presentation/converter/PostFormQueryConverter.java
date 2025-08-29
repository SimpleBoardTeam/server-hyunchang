package com.simpleboard.board.board.presentation.converter;

import com.simpleboard.board.board.application.dto.request.PostDetailsQuery;
import com.simpleboard.board.board.domain.common.vo.Visitor;
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
}
