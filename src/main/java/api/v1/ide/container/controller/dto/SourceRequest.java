package api.v1.ide.container.controller.dto;

import org.springframework.util.Assert;

public record SourceRequest(
        String source,
        Long userId
) {
    public SourceRequest {
        Assert.hasText(source, "소스 코드 정보를 입력해주세요.");
        Assert.notNull(userId, "사용자 id를 입력해주세요.");
    }
}
