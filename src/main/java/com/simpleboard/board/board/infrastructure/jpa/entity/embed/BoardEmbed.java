package com.simpleboard.board.board.infrastructure.jpa.entity.embed;

import jakarta.persistence.Embeddable;
import lombok.Builder;

@Embeddable
@Builder
public record BoardEmbed(Long boardId) {}
