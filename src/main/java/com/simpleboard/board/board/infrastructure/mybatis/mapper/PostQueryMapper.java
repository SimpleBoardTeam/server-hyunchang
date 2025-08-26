package com.simpleboard.board.board.infrastructure.mybatis.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

/**
 * <b>Mybatis용 Mapper 클래스</b>
 *
 * <p>Post 조회 요청을 받아 xml 파일에 정의되어 있는 SQL 쿼리 실행</p>
 */
@Mapper
public interface PostQueryMapper {
  PostDetailsData getPostDetails(PostDetailsCondition condition);

  List<String> getPostHashTags(@Param("postId") Long postId);
}
