package com.simpleboard.board.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <b>Error 응답 모델</b>
 *
 * <p>Error 응답에 추가적인 데이터가 필요하다면 해당 클래스를 extends하여 처리한다.
 *
 * <p>Res: Client <- Exception handler
 *
 * @domain response-dto
 */
@AllArgsConstructor
@Getter
public class ErrorResponse {
  String code;
  String message;

  /* 일반 예외용 */
  public static ErrorResponse of(ErrorCode e) {
    return new ErrorResponse(e.getCode(), e.getMessage());
  }
}
