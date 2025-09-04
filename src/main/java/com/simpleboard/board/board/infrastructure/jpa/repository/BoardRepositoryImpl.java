package com.simpleboard.board.board.infrastructure.jpa.repository;

import com.simpleboard.board.board.domain.board.entity.Board;
import com.simpleboard.board.board.domain.board.repository.BoardRepository;
import com.simpleboard.board.board.infrastructure.jpa.entity.BoardEntity;
import com.simpleboard.board.board.infrastructure.jpa.mapper.BoardMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

  private final BoardEntityRepository boardEntityRepository;
  private final BoardMapper boardMapper;

  @Override
  public Board save(Board board) {
    BoardEntity toSave = boardMapper.toEntity(board);
    BoardEntity saved = boardEntityRepository.save(toSave);
    return boardMapper.toDomain(saved);
  }

  @Override
  public Optional<Board> findByBoardName(String boardName) {
    return boardEntityRepository.findByBoardName(boardName).map(boardMapper::toDomain);
  }

  @Override
  public Optional<Board> findById(Long boardId) {
    return boardEntityRepository.findById(boardId).map(boardMapper::toDomain);
  }

  @Override
  public boolean existsByNameNormalized(String normalizedName) {
    return boardEntityRepository.existsByNormalizedName(normalizedName);
  }

  @Override
  public void deleteById(Long boardId) {
    boardEntityRepository.deleteById(boardId);
  }
}
