package api.v1.auth.join.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckDuplicateResponseDto {

    //is 사라지는거 방지
//    @Getter(AccessLevel.NONE)
    private boolean duplicate;

    @Builder
    private CheckDuplicateResponseDto(boolean duplicate) {
        this.duplicate = duplicate;
    }
}