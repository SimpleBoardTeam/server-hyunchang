package com.simpleboard.board.board.domain.comment.entity;

import com.simpleboard.board.board.domain.comment.dto.CreateParams;
import com.simpleboard.board.board.domain.comment.dto.DeleteParams;
import com.simpleboard.board.board.domain.comment.exception.MemberCommentPermissionException;

public class MemberComment extends Comment{
    Long writerId;

    protected MemberComment(CreateParams params) {
        super(params);
        writerId = params.writerId();
    }

    @Override
    protected void checkPermission(DeleteParams params) {
        if(!writerId.equals(params.writerId())) throw new MemberCommentPermissionException();
    }
}
