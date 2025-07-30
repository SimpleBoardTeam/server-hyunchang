package com.simpleboard.board.board.infrastructure.jpa.entity.embed;

import com.simpleboard.board.board.infrastructure.jpa.entity.HashTagEntity;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TagsEmbed {

  /** TagEmbed의 tags는 외부에서 Setter를 통해 주입해주어야 한다. */
  @Setter
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "POST_HASHTAG",
      joinColumns = @JoinColumn(name = "POST_ID"),
      inverseJoinColumns = @JoinColumn(name = "HASHTAG_ID"))
  List<HashTagEntity> tags;
}
