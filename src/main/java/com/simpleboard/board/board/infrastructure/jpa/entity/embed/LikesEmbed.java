package com.simpleboard.board.board.infrastructure.jpa.entity.embed;

import com.simpleboard.board.board.infrastructure.jpa.entity.PostEntity;
import com.simpleboard.board.board.infrastructure.jpa.entity.PostLikeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.*;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LikesEmbed {
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  List<PostLikeEntity> likes;

  public void setPost(PostEntity post) {
    likes.stream().forEach(like -> like.setPost(post));
  }
}
