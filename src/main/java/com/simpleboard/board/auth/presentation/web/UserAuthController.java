package com.simpleboard.board.auth.presentation.web;

import static com.simpleboard.board.auth.presentation.util.AuthStringProvider.Cookie.REGISTER_EMAIL_COOKIE;

import com.simpleboard.board.auth.application.dto.request.EmailMemberRegisterCommand;
import com.simpleboard.board.auth.application.service.AuthPrincipal;
import com.simpleboard.board.auth.application.service.UserAuthCommandService;
import com.simpleboard.board.auth.domain.token.exception.RefreshTokenInvalidException;
import com.simpleboard.board.auth.presentation.dto.request.EmailRegisterForm;
import com.simpleboard.board.auth.presentation.exception.EmailRegisterTokenException;
import com.simpleboard.board.auth.presentation.util.AuthStringProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "회원 인증 정보", description = "회원의 가입, 탈퇴 관련 API")
public class UserAuthController {

  private final UserAuthCommandService userAuthCommandService;

  @Operation(summary = "Email 회원가입", description = "email 토큰과 nickname 토큰을 통해 Email 유저 회원가입을 진행")
  @PostMapping("/register/email")
  public ResponseEntity<Void> registerEmailUser(
      HttpServletRequest request, @RequestBody @Valid EmailRegisterForm form) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) throw new EmailRegisterTokenException();
    String emailToken = getCookieToken(cookies, REGISTER_EMAIL_COOKIE);
    String nicknameToken =
        getCookieToken(cookies, AuthStringProvider.Cookie.REGISTER_NICKNAME_COOKIE);

    EmailMemberRegisterCommand command =
        EmailMemberRegisterCommand.builder()
            .birthYear(form.birthYear())
            .password(form.password())
            .gender(form.gender())
            .emailTokenRaw(emailToken)
            .nicknameTokenRaw(nicknameToken)
            .build();

    userAuthCommandService.emailMemberRegister(command);
    return ResponseEntity.created(URI.create("/login")).build();
  }

  @Operation(summary = "회원 탈퇴", description = "로그인된 유저의 회원 탈퇴를 진행합니다.")
  @DeleteMapping("/user")
  public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal AuthPrincipal authPrincipal) {
    Long userId = authPrincipal.getUserId();
    userAuthCommandService.deactivateAccount(userId);
    return ResponseEntity.noContent().build();
  }

  private String getCookieToken(Cookie[] cookies, String cookieName) {
    String refreshTokenRaw =
        Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(cookieName))
            .findFirst()
            .orElseThrow(RefreshTokenInvalidException::new)
            .getValue();
    return refreshTokenRaw;
  }
}
