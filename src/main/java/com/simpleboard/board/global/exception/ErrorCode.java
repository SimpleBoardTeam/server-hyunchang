package com.simpleboard.board.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * <b>Error Code Enum<b>
 *
 * <p>Service Exception 클래스를 사용한 커스텀 예외 정의 Enum
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
  /* Service Exception */
  // Post
  POST_PASSWORD_NOT_MATCH(
      HttpStatus.BAD_REQUEST, "400_POST_PASSWORD_NOT_MATCH", "Post의 비밀번호가 일치하지 않습니다."),
  POST_NO_DELETE_PERMISSION(
      HttpStatus.BAD_REQUEST, "400_POST_NO_DELETE_PERMISSION", "Post에 대한 삭제 권한이 없습니다."),
  POST_HASHTAG_SIZE_EXCEED(
      HttpStatus.BAD_REQUEST, "400_HASHTAG_SIZE_TOO_LARGE", "Hashtag의 개수가 너무 많습니다."),
  POST_NOT_FOUNT(HttpStatus.NOT_FOUND, "404_POST_NOT_FOUND", "Post를 찾을 수 없습니다."),

  // Comment
  COMMENT_MEMBER_NO_PERMISSION(
      HttpStatus.FORBIDDEN, "403_COMMENT_MEMBER_NO_PERMISSION", "댓글에 대한 권한이 없는 유저입니다."),
  COMMENT_PASSWORD_NOT_MATCH(
      HttpStatus.BAD_REQUEST, "400_COMMENT_PASSWORD_NOT_MATCH", "댓글의 비밀번호가 일치하지 않습니다."),

  // Board
  BOARD_NAME_DUPLICATED(HttpStatus.CONFLICT, "409_BOARD_NAME_DUPLICATED", "이미 존재하는 보드명입니다."),
  BOARD_NAME_INVALID(HttpStatus.BAD_REQUEST, "400_BOARD_NAME_INVALID", "보드명이 유효하지 않습니다."),

  // Member
  INVALID_BIRTH_YEAR(HttpStatus.BAD_REQUEST, "400_INVALID_BIRTH_YEAR", "출생 연도가 유효하지 않습니다."),
  DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "409_DUPLICATED_NICKNAME", "이미 사용 중인 닉네임입니다."),

  // Permission
  ASSIGNMENT_NOT_FOUND(
      HttpStatus.NOT_FOUND, "404_PERMISSION_ASSIGNMENT_NOT_FOUND", "권한 할당 정보를 찾을 수 없습니다."),
  ROLE_NOT_ASSIGNED(
      HttpStatus.FORBIDDEN, "403_PERMISSION_ROLE_NOT_ASSIGNED", "해당 사용자는 해당 역할이 부여되어 있지 않습니다."),

  // Auth
  USER_DEACTIVATED(HttpStatus.BAD_REQUEST, "400_USER_DEACTIVATED", "유저의 계정이 비활성화 상태입니다."),
  TOKEN_BLOCKED(HttpStatus.UNAUTHORIZED, "401_TOKEN_BLOCKED", "비활성화된 토큰입니다."),
  TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "401_TOKEN_EXPIRED", "만료된 토큰입니다."),
  TOKEN_PARSE_FAIL(HttpStatus.BAD_REQUEST, "400_TOKEN_ERROR", "유효하지 않은 토큰입니다."),
  EMAIL_CONFLICT(HttpStatus.CONFLICT, "409_EMAIL_CONFLICT", "이미 가입된 email입니다."),
  NICKNAME_CONFLICT(HttpStatus.CONFLICT, "409_NICKNAME_CONFLICT", "이미 사용중인 nickname입니다."),
  EMAIL_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "400_EMAIL_CODE_MISMATCH", "email 코드가 일치하지 않습니다."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "404_USER_NOT_FOUND", "일치하는 유저가 존재하지 않습니다."),

  /* Internal Error */
  INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500_INTERNAL", "서버 내부 오류가 발생했습니다."),
  DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500_DATABASE", "데이터베이스 처리 중 오류가 발생했습니다."),
  UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500_UNKNOWN", "알 수 없는 서버 오류가 발생했습니다."),

  /* Request Error */
  INVALID_INPUT(HttpStatus.BAD_REQUEST, "400_INVALID_INPUT", "입력 값이 올바르지 않습니다."),
  MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "400_MISSING_PARAM", "필수 파라미터가 누락되었습니다."),
  VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "400_VALIDATION_FAIL", "유효성 검증에 실패했습니다."),

  API_NOT_FOUND(HttpStatus.NOT_FOUND, "404_API_NOT_FOUND", "요청한 API를 찾을 수 없습니다."),
  NO_SUCH_RESOURCE(HttpStatus.NOT_FOUND, "404_API_NOT_FOUND", "요청한 리소스를 찾을 수 없습니다."),

  METHOD_NOT_ALLOWED(
      HttpStatus.METHOD_NOT_ALLOWED, "405_METHOD_NOT_ALLOWED", "허용되지 않은 HTTP 메서드입니다."),

  /* WebClient Error */
  WEBCLIENT_4XX_ERROR(
      HttpStatus.BAD_REQUEST, "400_WEBCLIENT_4XX_ERROR", "WebClient 요청 중 클라이언트 오류가 발생했습니다."),
  WEBCLIENT_5XX_ERROR(
      HttpStatus.BAD_GATEWAY, "502_WEBCLIENT_5XX_ERROR", "WebClient 요청 중 서버 오류가 발생했습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}
