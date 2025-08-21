package com.simpleboard.board.board.application.service;

import com.simpleboard.board.board.application.dto.request.BoardCreateCommand;
import com.simpleboard.board.board.application.dto.request.BoardDeleteCommand;
import com.simpleboard.board.board.application.dto.response.BoardCreateResult;
import com.simpleboard.board.board.domain.common.vo.Visitor;

public interface BoardCommandService {
  BoardCreateResult createBoard(Visitor visitor, BoardCreateCommand command);

  void deleteBoard(Visitor visitor, BoardDeleteCommand command);
}
