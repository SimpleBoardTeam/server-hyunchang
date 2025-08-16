package com.simpleboard.board.board.domain.board.vo;

import static org.assertj.core.api.Assertions.*;

import com.simpleboard.board.board.domain.board.exception.InvalidBoardNameException;
import java.text.Normalizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardNameTest {

  @Test
  @DisplayName("보드명은 정상적으로 생성된다")
  void createBoardName_success() {
    BoardName name = BoardName.of("  MyBoard  ");
    assertThat(name.toString()).isEqualTo("MyBoard"); // trim 적용 확인
    assertThat(name).isEqualTo(BoardName.of("MyBoard")); // equals/hashCode 확인
  }

  @Test
  @DisplayName("보드명이 null이면 예외가 발생한다")
  void createBoardName_null_fail() {
    assertThatThrownBy(() -> BoardName.of(null)).isInstanceOf(InvalidBoardNameException.class);
  }

  @Test
  @DisplayName("보드명이 공백이면 예외가 발생한다")
  void createBoardName_blank_fail() {
    assertThatThrownBy(() -> BoardName.of("   ")).isInstanceOf(InvalidBoardNameException.class);
  }

  @Test
  @DisplayName("보드명이 2자 미만이면 예외가 발생한다")
  void createBoardName_tooShort_fail() {
    assertThatThrownBy(() -> BoardName.of("A")).isInstanceOf(InvalidBoardNameException.class);
  }

  @Test
  @DisplayName("보드명이 30자 초과면 예외가 발생한다")
  void createBoardName_tooLong_fail() {
    String over30 = "a".repeat(31);
    assertThatThrownBy(() -> BoardName.of(over30)).isInstanceOf(InvalidBoardNameException.class);
  }

  @Test
  @DisplayName("normalized()는 소문자와 NFC 정규화를 적용한다")
  void normalized_success() {
    BoardName name1 = BoardName.of(" MyBoard ");
    BoardName name2 = BoardName.of("myboard");
    assertThat(name1.normalized()).isEqualTo(name2.normalized());

    // 한글 정규화(NFD vs NFC)도 동일하게 맞춰짐 (2자 이상 보장)
    String base = "가가"; // 최소 2자 보장
    String nfd = Normalizer.normalize(base, Normalizer.Form.NFD);
    String nfc = Normalizer.normalize(base, Normalizer.Form.NFC);

    assertThat(BoardName.of(nfd).normalized()).isEqualTo(BoardName.of(nfc).normalized());
  }
}
