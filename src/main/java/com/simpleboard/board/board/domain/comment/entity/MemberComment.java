package com.simpleboard.board.board.domain.comment.entity;

import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.dto.CommentDeleteParams;
import com.simpleboard.board.board.domain.comment.exception.MemberCommentPermissionException;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import lombok.Getter;

@Getter
public class MemberComment extends Comment{
    private Long writerId;

    protected MemberComment(CommentCreateParams params) {
        super(params);
        writerId = params.writerId();
    }

    @Override
    protected void checkPermission(Visitor visitor, CommentDeleteParams params) {
        if(!writerId.equals(visitor.memberId())) throw new MemberCommentPermissionException();
    }
}
