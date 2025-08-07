package com.simpleboard.board.global.exception;

import com.simpleboard.board.global.exception.webclient.WebClient4xxException;
import com.simpleboard.board.global.exception.webclient.WebClient5xxException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * <b>>Global exception handler</b>
 *
 * <p>ServiceException 상속 클래스 + unhandled exception 핸들링
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // TODO: MDC에 request ID 삽입 기능 추가
  private String rid() { // Request-ID 를 MDC에서 꺼내기 (필터에서 put)
    return MDC.get("rid");
  }

  // TODO: logging 분리하기
  private void logException(HttpServletRequest req, Exception e) {
    log.error(
        "[EXCEPTION] rid={} uri={} method={} type={} msg={}",
        rid(),
        req.getRequestURI(),
        req.getMethod(),
        e.getClass().getSimpleName(),
        e.getMessage(),
        e);
  }

  private ResponseEntity<ErrorResponse> buildResponse(ErrorCode errorCode) {
    return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
  }

  /** 1. 커스텀 예외 */
  @ExceptionHandler(ServiceException.class)
  private ResponseEntity<ErrorResponse> handleCustomException(
      HttpServletRequest request, ServiceException e) {
    logException(request, e);
    return buildResponse(e.getErrorCode());
  }

  /** 2. Bean Validation 실패 */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException e, HttpServletRequest req) {
    logException(req, e);
    return buildResponse(ErrorCode.INVALID_INPUT);
  }

  /** 3. 파라미터 누락 */
  @ExceptionHandler(MissingRequestValueException.class)
  public ResponseEntity<ErrorResponse> handleMissingParameterException(
      MissingRequestValueException ex, HttpServletRequest req) {
    logException(req, ex);
    return buildResponse(ErrorCode.MISSING_PARAMETER);
  }

  /** 4. HTTP 메서드 오류 */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotAllowedException(
      HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
    logException(req, ex);
    return buildResponse(ErrorCode.METHOD_NOT_ALLOWED);
  }

  /** 5. 리소스 없음 */
  @ExceptionHandler({NoSuchElementException.class})
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      NoSuchElementException ex, HttpServletRequest req) {
    logException(req, ex);
    return buildResponse(ErrorCode.NO_SUCH_RESOURCE);
  }

  /** 6. 존재하지 않는 API */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoHandler(
      NoHandlerFoundException ex, HttpServletRequest req) {
    logException(req, ex);
    return buildResponse(ErrorCode.API_NOT_FOUND);
  }

  /** 7. WebClient – 4xx 예외 처리 */
  @ExceptionHandler(WebClient4xxException.class)
  public ResponseEntity<ErrorResponse> handleClientError(WebClient4xxException ex, HttpServletRequest req) {
    logException(req, ex);
    return buildResponse(ErrorCode.WEBCLIENT_4XX_ERROR);
  }

  /** 8. WebClient – 5xx 예외 처리 */
  @ExceptionHandler(WebClient5xxException.class)
  public ResponseEntity<ErrorResponse> handleServerError(WebClient5xxException ex, HttpServletRequest req) {
    logException(req, ex);
    return buildResponse(ErrorCode.WEBCLIENT_5XX_ERROR);
  }

  /** 9. 예상치 못한 모든 예외 – 500 Fallback */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnknown(Exception ex, HttpServletRequest req) {
    logException(req, ex);
    return buildResponse(ErrorCode.UNKNOWN_ERROR);
  }
}
