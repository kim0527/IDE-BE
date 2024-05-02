package api.v1.auth.login.controller;

import api.v1.auth.kakao.KakaoProfile;
import api.v1.auth.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/ide/login")
public class loginController {

    private final KakaoService kakaoService;

    /*
    Test용
     */
    @GetMapping
    public String loginPage(Model model) {
        model.addAttribute("kakaoApiKey", kakaoService.getClientId());
        model.addAttribute("redirectUri", kakaoService.getRedirectUri());
        return "kakaoLogin";
    }

    @RequestMapping("/kakao")
//    public ResponseEntity<LoginResponseDto> isUser(@RequestBody IsUserRequestDto isUserRequestDto){
//        ResponseEntity<LoginResponseDto>
    public String kakaoLogin(@RequestParam(value="code")String code){
        // 인가 코드 받기
//        final String code = isUserRequestDto.getCode();
        // 인가 코드를 통해서 accessToken 받기
        final String accessToken = kakaoService.getAccessToken(code).getAccess_token();

        KakaoProfile userInfo = kakaoService.getUserInfo(accessToken);
        Integer id = userInfo.getId();


        String nickname = (String)userInfo.getNickname();

        log.info("id = " + id);
        log.info("nickname = " + nickname);
        log.info("accessToken = " + accessToken);

        return "success";
    }

}