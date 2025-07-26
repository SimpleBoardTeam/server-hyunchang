package com.simpleboard.board.board.domain.comment.entity;

import com.simpleboard.board.board.domain.comment.dto.CreateParams;
import com.simpleboard.board.board.domain.comment.dto.DeleteParams;
import com.simpleboard.board.board.domain.comment.exception.CommentPasswordException;
import com.simpleboard.board.board.domain.comment.vo.CommentType;

public class GuestComment extends Comment {
    String nickname;
    String password;

    public GuestComment(CreateParams params) {
        super(params);
        this.nickname = params.nickname();
        this.password = params.password();
    }

    @Override
    protected void checkPermission(DeleteParams params) {
        if(!password.equals(params.password())) throw new CommentPasswordException();
    }
}
