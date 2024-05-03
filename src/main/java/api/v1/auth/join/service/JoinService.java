package api.v1.auth.join.service;

import api.v1.auth.join.dto.JoinRequestDto;
import api.v1.auth.join.dto.UserJoinDto;
import api.v1.auth.kakao.KakaoProfile;
import api.v1.auth.kakao.KakaoService;
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
public class JoinService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final KakaoService kakaoService;

    @Transactional
    public UserJoinDto join(JoinRequestDto joinRequestDto) {
        final User user = createUser(joinRequestDto);

        userRepository.save(user);

        final String accessToken = jwtService.generateAccessToken(user, new Date(System.nanoTime()));
        final String refreshToken = jwtService.generateRefreshToken(user, new Date(System.nanoTime()));

        return UserJoinDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(user.getId())
                .build();
    }

    private User createUser(JoinRequestDto joinRequestDto) {
        final String code = joinRequestDto.getCode();
        final KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(code);

        final User user = User.builder()
                .kakaoId(kakaoProfile.getId())
                .name(kakaoProfile.getNickname())
                .profileImg(kakaoProfile.getProfileImage())
                .nickname(joinRequestDto.getNickname())
                .birthDate(joinRequestDto.getBirthDate())
                .role("ROLE_ADMIN")
                .build();
        return user;
    }

    public boolean isNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    public boolean isKakaoId(Integer kakaoId){
        return userRepository.existsByKakaoId(kakaoId);
    }

}
