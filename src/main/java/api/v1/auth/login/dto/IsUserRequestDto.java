package api.v1.auth.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IsUserRequestDto {

    private String provider;
    private String code;

    @Builder
    private IsUserRequestDto(String provider, String code) {
        this.provider = provider;
        this.code = code;
    }
}
