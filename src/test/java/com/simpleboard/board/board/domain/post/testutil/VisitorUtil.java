package com.simpleboard.board.board.domain.post.testutil;

import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;

/**
 * <b>Test utility 클래스
 *
 * <p>Guest/Member visitor를 생성
 */
public class VisitorUtil {
  public static Visitor guest(String vid) {
    return new Visitor(VisitorType.GUEST, vid, null);
  }

  public static Visitor member(String vid, long memberId) {
    return new Visitor(VisitorType.MEMBER, vid, memberId);
  }
}
