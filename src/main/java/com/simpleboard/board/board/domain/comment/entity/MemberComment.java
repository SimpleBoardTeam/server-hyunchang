package com.simpleboard.board.board.domain.comment.entity;

import com.simpleboard.board.board.domain.comment.dto.CreateParams;

public class MemberComment extends Comment{
    Long writerId;

    protected MemberComment(CreateParams params) {
        super(params);
        writerId = params.writerId();
    }
}
