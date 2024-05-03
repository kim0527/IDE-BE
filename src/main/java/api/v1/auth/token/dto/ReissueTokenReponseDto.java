package api.v1.auth.token.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissueTokenReponseDto {
    private String token;

    @Builder
    private ReissueTokenReponseDto(String token) {
        this.token = token;
    }
}
