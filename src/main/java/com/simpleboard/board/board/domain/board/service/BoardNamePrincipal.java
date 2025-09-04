package com.simpleboard.board.board.domain.board.service;

import com.simpleboard.board.board.domain.board.exception.DuplicateBoardNameException;
import com.simpleboard.board.board.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * <b>BoardNameUniquenessChecker</b> Domain Service.
 *
 * <p>보드명 중복 여부를 확인하는 도메인 서비스
 *
 * @domain domain-service
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class BoardNamePrincipal {
  private final BoardRepository boardRepository;

  /**
   * 보드명의 유일성을 보장합니다.
   *
   * <p>보드명이 이미 존재할 경우 {@link DuplicateBoardNameException}을 발생시켜 도메인 규칙(보드명은 유일해야 함)을 강제합니다.
   *
   * @param name 유일성 검사를 수행할 보드명
   * @throws DuplicateBoardNameException 보드명이 이미 존재하는 경우
   * @since 1.0
   */
  public void ensureUnique(String name) {
    if (boardRepository.existsByNameNormalized(name)) {
      throw new DuplicateBoardNameException("이미 존재하는 보드명: " + name);
    }
  }
}
