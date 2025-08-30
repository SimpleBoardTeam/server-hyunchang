package com.simpleboard.board.auth.application.service;

import com.simpleboard.board.auth.domain.token.dto.request.RefreshTokenRotationParam;
import com.simpleboard.board.auth.domain.token.dto.request.VerifyTokenIssueParam;
import com.simpleboard.board.auth.domain.token.service.TokenDomainService;
import com.simpleboard.board.auth.domain.token.vo.Token;
import com.simpleboard.board.auth.domain.token.vo.TokenPair;
import com.simpleboard.board.auth.domain.token.vo.VerifyPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenApplicationServiceImpl implements TokenApplicationService {

    private final TokenDomainService tokenDomainService;

    @Override
    public TokenPair rotateRefreshToken(String refreshTokenRaw) {
        return tokenDomainService.rotateRefreshToken(
                RefreshTokenRotationParam.builder()
                        .oldRefreshRaw(refreshTokenRaw).build());
    }

    @Override
    public Token issueVerifyToken(String subject, VerifyPurpose purpose) {
        return tokenDomainService.issueVerifyToken(
                VerifyTokenIssueParam.builder()
                        .subject(subject)
                        .purpose(purpose).build());
    }

    @Override
    public void enrollBlacklist(String tokenRaw) {
        tokenDomainService.enrollBlacklist(tokenRaw);
    }

    @Override
    public void blockSnatchedToken(String tokenRaw) {
        tokenDomainService.blockTokenUser(tokenRaw);
    }
}
