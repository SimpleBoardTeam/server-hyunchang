package com.simpleboard.board.board.infrastructure.jpa.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.simpleboard.board.board.domain.board.entity.Board;
import com.simpleboard.board.board.domain.board.vo.BoardName;
import com.simpleboard.board.board.domain.board.vo.Manager;
import com.simpleboard.board.board.infrastructure.jpa.entity.BoardEntity;
import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardMapperTest {

  private BoardMapper mapper;

  @BeforeEach
  void setUp() throws Exception {
    // BoardMapper는 private 생성자라 리플렉션으로 인스턴스화
    Constructor<BoardMapper> ctor = BoardMapper.class.getDeclaredConstructor();
    ctor.setAccessible(true);
    mapper = ctor.newInstance();
  }

  @Test
  @DisplayName("toEntity: Domain → Entity 매핑")
  void toEntity_maps_all_fields() {
    // given
    Long boardId = 1L;
    BoardName name = BoardName.of("notice");
    Manager manager = Manager.of(1001L);
    LocalDateTime createdAt = LocalDateTime.now();

    Board domain = Board.reconstruct(boardId, name, manager, createdAt);

    // when
    BoardEntity entity = mapper.toEntity(domain);

    // then
    assertThat(entity).isNotNull();
    assertThat(entity.getBoardId()).isEqualTo(boardId);
    assertThat(entity.getBoardName()).isEqualTo(name.toString());
    assertThat(entity.getManagerId()).isEqualTo(manager.memberId());
  }

  @Test
  @DisplayName("toDomain: Entity → Domain 매핑")
  void toDomain_maps_all_fields() {
    // given
    Long boardId = 10L;
    BoardName boardName = BoardName.of("free");
    Long managerId = 2002L;

    BoardEntity entity = new BoardEntity(boardId, boardName.toString(), boardName.normalized(), managerId);

    // when
    Board domain = mapper.toDomain(entity);

    // then
    assertThat(domain).isNotNull();
    assertThat(domain.getBoardId()).isEqualTo(boardId);
    assertThat(domain.getBoardName().toString()).isEqualTo(boardName);
    assertThat(domain.getManager().memberId()).isEqualTo(managerId);
  }
}
