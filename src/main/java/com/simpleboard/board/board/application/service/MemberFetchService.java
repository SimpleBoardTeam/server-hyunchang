package com.simpleboard.board.board.application.service;

/** <b>Member Context에 대한 데이터 요청을 담당하는 클래스</b> */
public interface MemberFetchService {
  String fetchNickname(Long memberId);
}
