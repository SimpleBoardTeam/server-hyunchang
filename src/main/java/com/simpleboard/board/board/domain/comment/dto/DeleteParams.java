package com.simpleboard.board.board.domain.comment.dto;


import com.simpleboard.board.board.domain.comment.vo.CommentType;

public record DeleteParams(
        CommentType commentType,
        Long writerId,
        String nickname,
        String password
) {
}
