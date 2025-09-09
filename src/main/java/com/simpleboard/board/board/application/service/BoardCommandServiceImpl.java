package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.dto.request.BoardCreateCommand;
import com.simpleboard.board.board.application.dto.request.BoardDeleteCommand;
import com.simpleboard.board.board.application.dto.response.BoardCreateResult;
import com.simpleboard.board.board.application.exception.BoardNotFoundException;
import com.simpleboard.board.board.domain.board.entity.Board;
import com.simpleboard.board.board.domain.board.repository.BoardRepository;
import com.simpleboard.board.board.domain.board.service.BoardNamePrincipal;
import com.simpleboard.board.board.domain.board.vo.BoardName;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardCommandServiceImpl implements BoardCommandService {

  private final BoardRepository boardRepository;
  private final BoardNamePrincipal boardNamePrincipal;
  private final PermissionCheckService permissionCheckService;

  @Override
  public BoardCreateResult createBoard(Visitor visitor, BoardCreateCommand command) {
    permissionCheckService.checkBoardCreatePermission(visitor.memberId());

    Board newBoard = Board.create(BoardName.of(command.boardName()), visitor, boardNamePrincipal);
    Board savedBoard = boardRepository.save(newBoard);
    return new BoardCreateResult(savedBoard.getBoardId());
  }

  @Override
  public void deleteBoard(Visitor visitor, BoardDeleteCommand command) {
    String boardName = command.boardName();
    Board board =
        boardRepository.findByBoardName(boardName).orElseThrow(BoardNotFoundException::new);
    Long boardId = board.getBoardId();

    permissionCheckService.checkBoardDeletePermission(visitor.memberId(), boardId);

    boardRepository.deleteById(boardId);
  }
}
