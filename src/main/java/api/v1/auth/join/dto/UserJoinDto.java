package api.v1.auth.join.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserJoinDto {
    private String accessToken;
    private String refreshToken;
    private Long id;

    @Builder
    private UserJoinDto(String accessToken, String refreshToken, Long id) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
    }
}