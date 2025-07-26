package com.simpleboard.board.board.domain.comment.exception;

import com.simpleboard.board.global.exception.ErrorCode;
import com.simpleboard.board.global.exception.ServiceException;

/**
 * MemberComment 삭제시 작성자-요청자 불일치 exception
 */
public class MemberCommentPermissionException extends ServiceException {
    private static final ErrorCode ERROR_CODE = ErrorCode.COMMENT_MEMBER_NO_PERMISSION;

    public MemberCommentPermissionException() {
        super(ERROR_CODE, null);
    }
}
