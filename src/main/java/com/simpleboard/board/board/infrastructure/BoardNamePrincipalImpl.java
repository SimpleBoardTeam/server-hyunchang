package com.simpleboard.board.board.infrastructure;

import com.simpleboard.board.board.domain.board.repository.BoardRepository;
import com.simpleboard.board.board.domain.board.service.BoardNamePrincipal;
import com.simpleboard.board.board.domain.board.vo.BoardName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardNamePrincipalImpl implements BoardNamePrincipal {

  private final BoardRepository boardRepository;

  @Override
  public boolean isUnique(BoardName name) {
    return !boardRepository.existsByNameNormalized(name.toString());
  }
}
