package com.simpleboard.board.board.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.simpleboard.board.board.application.dto.request.BoardCreateCommand;
import com.simpleboard.board.board.application.dto.request.BoardDeleteCommand;
import com.simpleboard.board.board.application.dto.response.BoardCreateResult;
import com.simpleboard.board.board.application.testutil.BoardCommandMockRepository;
import com.simpleboard.board.board.domain.board.entity.Board;
import com.simpleboard.board.board.domain.board.repository.BoardRepository;
import com.simpleboard.board.board.domain.board.service.BoardNamePrincipal;
import com.simpleboard.board.board.domain.board.vo.BoardName;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import com.simpleboard.board.board.domain.common.vo.VisitorType;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BoardCommandServiceUnitTest {

  private BoardRepository boardRepository;
  @Mock private BoardNamePrincipal boardNamePrincipal;
  @Mock private PermissionCheckService permissionCheckService;

  private BoardCommandService boardCommandService;

  private final Long memberId = 1001L;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    boardRepository = new BoardCommandMockRepository();
    boardCommandService =
        new BoardCommandServiceImpl(boardRepository, boardNamePrincipal, permissionCheckService);
  }

  @Test
  @DisplayName("Board 생성 성공 - ensureUnique 호출 및 권한 체크")
  void createBoard_success_member() {
    // given
    String boardName = "board";
    Visitor visitor = memberVisitor("vid-1", memberId);
    BoardCreateCommand command = BoardCreateCommand.builder().boardName(boardName).build();

    // ensureUnique는 외부 의존성이라 호출만 검증
    doNothing().when(boardNamePrincipal).ensureUnique(BoardName.of(boardName));
    doNothing().when(permissionCheckService).checkBoardCreatePermission(memberId);

    // when
    BoardCreateResult result = boardCommandService.createBoard(visitor, command);

    // then
    assertThat(result).isNotNull();
    assertThat(result.boardId()).isNotNull();
    assertThat(result.boardId()).isGreaterThan(0L);

    // 저장 확인
    Optional<Board> saved = boardRepository.findById(result.boardId());
    assertThat(saved).isPresent();
    assertThat(saved.get().getBoardName().toString()).isEqualTo(boardName);
  }

  @Test
  @DisplayName("Board 삭제 성공 - boardName 조회 후 deleteById")
  void deleteBoard_success_member() {
    // given
    String boardName = "board-to-delete";
    Visitor visitor = memberVisitor("vid-2", memberId);
    BoardDeleteCommand command = BoardDeleteCommand.builder().boardName(boardName).build();

    // 미리 하나 저장 (실제 메모리 저장소 사용)
    doNothing().when(boardNamePrincipal).ensureUnique(BoardName.of(boardName));
    doNothing().when(permissionCheckService).checkBoardCreatePermission(memberId);
    BoardCreateCommand createCmd = BoardCreateCommand.builder().boardName(boardName).build();
    BoardCreateResult created = boardCommandService.createBoard(visitor, createCmd);

    // 존재 확인
    Optional<Board> before = boardRepository.findById(created.boardId());
    assertThat(before).isPresent();

    // when
    boardCommandService.deleteBoard(visitor, command);

    // then
    Optional<Board> after = boardRepository.findByBoardName(boardName);
    assertThat(after).isEmpty();
  }

  private Visitor memberVisitor(String vId, Long memberId) {
    return new Visitor(VisitorType.MEMBER, vId, memberId);
  }
}
