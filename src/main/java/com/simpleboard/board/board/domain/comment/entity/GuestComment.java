package com.simpleboard.board.board.domain.comment.entity;

import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.dto.CommentDeleteParams;
import com.simpleboard.board.board.domain.comment.exception.CommentPasswordException;
import com.simpleboard.board.board.domain.common.vo.Visitor;

public class GuestComment extends Comment {
    String nickname;
    String password;

    public GuestComment(CommentCreateParams params) {
        super(params);
        this.nickname = params.nickname();
        this.password = params.password();
    }

    @Override
    protected void checkPermission(Visitor visitor, CommentDeleteParams params) {
        if(!password.equals(params.password())) throw new CommentPasswordException();
    }
}
