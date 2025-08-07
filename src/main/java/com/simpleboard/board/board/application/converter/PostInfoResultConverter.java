package com.simpleboard.board.board.application.converter;

import com.simpleboard.board.board.application.dto.response.PostToggleLikeResult;
import com.simpleboard.board.board.domain.post.dto.LikeInfo;
import org.springframework.stereotype.Component;

/**
 * <b>Post Info -> result Converter</b>
 *
 * <p>Post의 domain dto인 Info를 command dto인 result 클래스로 변환
 */
@Component
public class PostInfoResultConverter {

  public PostToggleLikeResult toggleLikeInfoToResult(LikeInfo info) {
    return PostToggleLikeResult.builder()
        .likeCount(info.likeCount())
        .isLiked(info.isLiked())
        .build();
  }
}
