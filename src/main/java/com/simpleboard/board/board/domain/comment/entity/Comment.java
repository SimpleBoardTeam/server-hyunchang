package com.simpleboard.board.board.domain.comment.entity;

import com.simpleboard.board.board.domain.comment.dto.CommentCreateParams;
import com.simpleboard.board.board.domain.comment.dto.CommentDeleteParams;
import com.simpleboard.board.board.domain.comment.vo.CommentState;
import com.simpleboard.board.board.domain.comment.vo.CommentType;
import com.simpleboard.board.board.domain.common.vo.Visitor;
import lombok.Getter;

@Getter
public abstract class Comment {

    private Long id;
    private Long parentId;
    private Long postId;
    private String content;
    private CommentState commentState;

    protected Comment(CommentCreateParams params){
        this.parentId = params.parentCommentId();
        this.postId = params.postId();
        this.content = params.content();
        this.commentState = CommentState.ACTIVATE;
    }

    public static Comment write(CommentCreateParams params) {
        if(params.commentType() == CommentType.GUEST) return new GuestComment(params);
        if(params.commentType() == CommentType.MEMBER) return new MemberComment(params);
        return null;
    }

    public Long deleteComment(Visitor visitor, CommentDeleteParams params){
        checkPermission(visitor, params);
        this.commentState = CommentState.DELETED;
        return this.id;
    }

    protected abstract void checkPermission(Visitor visitor, CommentDeleteParams params);
}
