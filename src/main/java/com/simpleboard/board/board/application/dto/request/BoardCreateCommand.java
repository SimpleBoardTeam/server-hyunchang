package com.simpleboard.board.board.application.dto.request;

import lombok.Builder;

@Builder
public record BoardCreateCommand(String boardName) {}
