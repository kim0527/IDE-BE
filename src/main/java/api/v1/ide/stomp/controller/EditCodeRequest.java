package api.v1.ide.stomp.controller;

import org.springframework.util.Assert;

public record EditCodeRequest(
        String code,
        String roomId
) {
    public EditCodeRequest {
        Assert.hasText(code, "입력이 올바르지 않습니다.");
        Assert.hasText(roomId, "roomId가 올바르지 않습니다.");
    }
}
