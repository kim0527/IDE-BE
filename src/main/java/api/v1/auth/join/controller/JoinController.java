package api.v1.auth.join.controller;

import api.v1.domain.User;
import api.v1.auth.join.dto.JoinRequestDto;
import api.v1.auth.join.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequestDto joinRequestDto){

        if(joinService.isKakaoId(joinRequestDto.getKakaoId())){
            return ResponseEntity.ok("이미 유저 입니다.");
        }

        if(joinService.isNickname(joinRequestDto.getNickname())){
            return ResponseEntity.ok("이미 존재하는 닉네임 입니다.");
        }

        final User user = User.builder()
                    .kakaoId(joinRequestDto.getKakaoId())
                    .name(joinRequestDto.getName())
                    .profileImg(joinRequestDto.getProfileImg())
                    .nickname(joinRequestDto.getNickname())
                    .birthDate(joinRequestDto.getBirthDate())
                    .role("ROLE_ADMIN")
                    .build();

        Long result = joinService.join(user);

        return ResponseEntity.ok("회원가입에 성공했습니다. ( Id = "+result+" )");
    }



}