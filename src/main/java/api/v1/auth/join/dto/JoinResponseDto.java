package api.v1.auth.join.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinResponseDto {

    private String token;
    private Long memberId;
    private boolean hasExtraDetails;

    @Builder
    private JoinResponseDto(String token, Long memberId, boolean hasExtraDetails) {
        this.token = token;
        this.memberId = memberId;
        this.hasExtraDetails = hasExtraDetails;
    }
}
