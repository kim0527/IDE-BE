package api.v1.auth.join.controller;

import api.v1.auth.join.dto.CheckDuplicateResponseDto;
import api.v1.auth.join.dto.JoinResponseDto;
import api.v1.auth.join.dto.UserJoinDto;
import api.v1.auth.token.CookieTokenHandler;
import api.v1.domain.User;
import api.v1.auth.join.dto.JoinRequestDto;
import api.v1.auth.join.service.JoinService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;
    private final CookieTokenHandler cookieTokenHandler;

    @PostMapping("/api/v1/auth/join")
    public ResponseEntity<JoinResponseDto> join(@RequestBody JoinRequestDto joinRequestDto, HttpServletResponse response){

        final UserJoinDto userJoinDto = joinService.join(joinRequestDto);

        cookieTokenHandler.setCookieToken(response,userJoinDto.getRefreshToken());
        final JoinResponseDto joinResponseDto = JoinResponseDto.builder()
                .memberId(userJoinDto.getId())
                .token(userJoinDto.getAccessToken())
                .hasExtraDetails(true)
                .build();

        return ResponseEntity.ok(joinResponseDto);
    }

    @GetMapping("/api/v1/auth/join/username-duplicate")
    public ResponseEntity<CheckDuplicateResponseDto>checkDuplication(@RequestParam("name") String nickname){
        final CheckDuplicateResponseDto response = CheckDuplicateResponseDto.builder().duplicate(joinService.isNickname(nickname)).build();
        return ResponseEntity.ok(response);

    }



}
