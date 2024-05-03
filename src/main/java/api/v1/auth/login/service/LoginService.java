package api.v1.auth.login.service;


import api.v1.auth.kakao.KakaoProfile;
import api.v1.auth.login.dto.UserLoginDto;
import api.v1.auth.token.RefreshToken;
import api.v1.auth.token.RefreshTokenRepository;
import api.v1.auth.token.jwt.JwtService;
import api.v1.domain.User;
import api.v1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public boolean isUser(Integer kakaoId){
        return userRepository.existsByKakaoId(kakaoId);
    }

    public User getUserByKakaoId(Integer kakaoId){
        User user = userRepository.findByKakaoId(kakaoId).orElseThrow();
        return user;
    }


    @Transactional
    public UserLoginDto login(KakaoProfile kakaoProfile){
        final User user = getUser(kakaoProfile);

        final String accessToken = jwtService.generateAccessToken(user, new Date(System.nanoTime()));
        final String refreshToken = jwtService.generateRefreshToken(user, new Date(System.nanoTime()));

        renewRefreshToken(user,refreshToken);

        return UserLoginDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(user.getId())
                .build();
    }

    @Transactional
    public void logout(String Token){
        final RefreshToken refreshToken = refreshTokenRepository.findByToken(Token).orElseThrow();
        refreshTokenRepository.delete(refreshToken);
    }

    private User getUser(KakaoProfile kakaoProfile) {
        if (isUser(kakaoProfile.getId())){
            return getUserByKakaoId(kakaoProfile.getId());
        }

        User user=User.builder()
                .kakaoId(kakaoProfile.getId())
                .name(kakaoProfile.getNickname())
                .profileImg(kakaoProfile.getProfileImage())
                .role("ROLE_ADMIN")
                .build();
        userRepository.save(user);
        return user;
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