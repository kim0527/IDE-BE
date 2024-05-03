package api.v1.auth.token.service;


import api.v1.auth.login.dto.UserLoginDto;
import api.v1.auth.token.RefreshToken;
import api.v1.auth.token.RefreshTokenRepository;
import api.v1.auth.token.jwt.JwtService;
import api.v1.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    public UserLoginDto reissueToken(String refreshToken){
        final RefreshToken token = refreshTokenRepository.findByToken(refreshToken).orElseThrow();
        final User user = token.getUser();

        String newAccessToken = jwtService.generateAccessToken(user, new Date(System.nanoTime()));
        String newRefreshToken = jwtService.generateRefreshToken(user, new Date(System.nanoTime()));

        renewRefreshToken(user, newRefreshToken);

        return UserLoginDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .id(user.getId())
                .build();
    }

    private void renewRefreshToken(User user, String refreshToken) {
        RefreshToken token = refreshTokenRepository.findTokenByUser(user)
                .orElse(createRefreshToken(user, refreshToken));

        token.updateToken(refreshToken);
        refreshTokenRepository.save(token);
    }

    private RefreshToken createRefreshToken(User user, String refreshToken) {
        return RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .build();
    }
}
