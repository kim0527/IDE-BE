package api.v1.auth.join.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class JoinRequestDto {

    private String provider;
    private String code;
    private String nickname;
    private LocalDate birthDate;

    @Builder
    private JoinRequestDto(String provider, String code, String nickname, LocalDate birthDate) {
        this.provider = provider;
        this.code = code;
        this.nickname = nickname;
        this.birthDate = birthDate;
    }


}
