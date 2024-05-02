package IDE.auth.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {

    private String token;
    private Long memberId;
    private boolean hasExtraDetails;

    @Builder
    private LoginResponseDto(String token, Long memberId, boolean hasExtraDetails) {
        this.token = token;
        this.memberId = memberId;
        this.hasExtraDetails = hasExtraDetails;
    }
}
