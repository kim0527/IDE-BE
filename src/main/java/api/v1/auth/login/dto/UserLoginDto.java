package api.v1.auth.login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginDto {
    private String accessToken;
    private String refreshToken;
    private Long id;

    @Builder
    private UserLoginDto(String accessToken, String refreshToken, Long id) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
    }
}