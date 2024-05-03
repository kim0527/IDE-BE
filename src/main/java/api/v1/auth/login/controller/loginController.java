package api.v1.auth.login.controller;

import api.v1.auth.kakao.KakaoProfile;
import api.v1.auth.kakao.KakaoService;
import api.v1.auth.login.dto.IsUserRequestDto;
import api.v1.auth.login.dto.LoginResponseDto;
import api.v1.auth.login.dto.UserLoginDto;
import api.v1.auth.login.service.LoginService;
import api.v1.auth.token.CookieTokenHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class loginController {

    private final LoginService loginService;
    private final KakaoService kakaoService;
    private final CookieTokenHandler cookieTokenHandler;

    /*
    Test용
     */
    @GetMapping("/ide/login")
    public String loginPage(Model model) {
        model.addAttribute("kakaoApiKey", kakaoService.getClientId());
        model.addAttribute("redirectUri", kakaoService.getRedirectUri());
        return "kakaoLogin";
    }

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody IsUserRequestDto isUserRequestDto, HttpServletResponse response){
        String code = isUserRequestDto.getCode();

        final KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(code);
        final UserLoginDto userLoginDto = loginService.login(kakaoProfile);

        cookieTokenHandler.setCookieToken(response,userLoginDto.getRefreshToken());

        final LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .memberId(userLoginDto.getId())
                .token(userLoginDto.getAccessToken())
                .hasExtraDetails(true)
                .build();

        return ResponseEntity.ok(loginResponseDto);
    }

    @GetMapping("/api/v1/auth/logout")
    public ResponseEntity<?> logout(@CookieValue("Refresh-Token") String refreshToken, HttpServletResponse response){

        System.out.println("refreshToken:"+refreshToken);
        loginService.logout(refreshToken);
        cookieTokenHandler.expireCookieToken(response);

        return ResponseEntity.ok().build();
    }

}
