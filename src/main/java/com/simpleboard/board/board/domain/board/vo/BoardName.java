package com.simpleboard.board.board.domain.board.vo;

import com.simpleboard.board.board.domain.board.exception.InvalidBoardNameException;
import java.text.Normalizer;
import java.util.Locale;
import lombok.EqualsAndHashCode;

/**
 * <b>BoardName</b> Value Object.
 *
 * <p>보드 이름을 캡슐화하여 유효성 검증과 불변성을 보장한다.
 *
 * @domain value-object
 * @since 1.0
 */
@EqualsAndHashCode
public class BoardName {

  private final String boardName;

  private BoardName(String boardName) {
    verifyBoardName(boardName);
    this.boardName = boardName.trim();
  }

  public static BoardName of(String boardName) {
    return new BoardName(boardName);
  }

  private void verifyBoardName(String boardName) {
    if (boardName == null || boardName.isBlank()) {
      throw new InvalidBoardNameException("보드명은 비어 있을 수 없습니다.");
    }
    String trimmed = boardName.trim();
    if (trimmed.length() < 2 || trimmed.length() > 30) {
      throw new InvalidBoardNameException("보드명은 2~30자여야 합니다.");
    }
  }

  public String normalized() {
    String nfc = Normalizer.normalize(boardName.trim(), Normalizer.Form.NFC);
    return nfc.toLowerCase(Locale.ROOT);
  }

  @Override
  public String toString() {
    return boardName;
  }
}
