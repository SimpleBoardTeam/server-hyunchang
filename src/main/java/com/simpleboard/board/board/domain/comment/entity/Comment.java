package com.simpleboard.board.board.domain.comment.entity;

import com.simpleboard.board.board.domain.comment.dto.CreateParams;
import com.simpleboard.board.board.domain.comment.dto.DeleteParams;
import com.simpleboard.board.board.domain.comment.vo.CommentState;
import com.simpleboard.board.board.domain.comment.vo.CommentType;

public abstract class Comment {

    Long id;
    Long parentId;
    Long postId;
    String content;
    CommentState commentState;

    protected Comment(CreateParams params){
        this.parentId = params.parentCommentId();
        this.postId = params.postId();
        this.content = params.content();
        this.commentState = CommentState.ACTIVATE;
    }

    public static Comment write(CreateParams params) {
        if(params.commentType() == CommentType.GUEST) return new GuestComment(params);
        if(params.commentType() == CommentType.MEMBER) return new MemberComment(params);
        return null;
    }

    public void deleteComment(DeleteParams params){
        checkPermission(params);
        this.commentState = CommentState.DELETED;
    }

    protected abstract void checkPermission(DeleteParams params);
}
