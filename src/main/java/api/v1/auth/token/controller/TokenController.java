package api.v1.auth.token.controller;

import api.v1.auth.login.dto.UserLoginDto;
import api.v1.auth.token.CookieTokenHandler;
import api.v1.auth.token.dto.ReissueTokenReponseDto;
import api.v1.auth.token.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final CookieTokenHandler cookieTokenHandler;

    @GetMapping("/api/v1/auth/reissue")
    public ResponseEntity<ReissueTokenReponseDto> reissueToken(@CookieValue("Refresh-Token") String refreshToken, HttpServletResponse response){

        final UserLoginDto userLoginDto = tokenService.reissueToken(refreshToken);

        cookieTokenHandler.setCookieToken(response, userLoginDto.getRefreshToken());
        final ReissueTokenReponseDto reponse = ReissueTokenReponseDto.builder().token(userLoginDto.getAccessToken()).build();

        return ResponseEntity.ok(reponse);

    }

}
