package com.simpleboard.board.board.domain.comment.vo;

/**
 * <b>Comment 상태 enum</b> 열거형.
 *
 * <p>Comment의 상태를 나타내는 enum</p>
 *
 * @domain enum
 * @since 1.0
 */
public enum CommentState {
    ACTIVATE,
    DELETED,
    COMMENT_ACTIVATE_POST_DELETED,
    COMMENT_DELETED_POST_DELETED,
}
