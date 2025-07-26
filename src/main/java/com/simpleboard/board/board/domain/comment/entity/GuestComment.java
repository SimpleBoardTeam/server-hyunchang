package com.simpleboard.board.board.domain.comment.entity;

import com.simpleboard.board.board.domain.comment.dto.CreateParams;
import com.simpleboard.board.board.domain.comment.vo.CommentType;

public class GuestComment extends Comment {
    String nickname;
    String password;

    public GuestComment(CreateParams params) {
        super(params);
        this.nickname = params.nickname();
        this.password = params.password();
    }
}
