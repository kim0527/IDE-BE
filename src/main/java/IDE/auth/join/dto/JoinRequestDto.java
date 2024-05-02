package IDE.auth.join.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class JoinRequestDto {

    private Long kakaoId;
    private String name;
    private String profileImg;
    private String nickname;
    private LocalDate birthDate;

    @Builder
    private JoinRequestDto(Long kakaoId, String name, String profileImg, String nickname, LocalDate birthDate) {
        this.kakaoId=kakaoId;
        this.name = name;
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.birthDate = birthDate;
    }

}
