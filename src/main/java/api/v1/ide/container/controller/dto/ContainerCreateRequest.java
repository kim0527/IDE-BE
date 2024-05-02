package api.v1.ide.container.controller.dto;

import org.springframework.util.Assert;

public record ContainerCreateRequest(
        Long userId
) {
    public ContainerCreateRequest {
        Assert.notNull(userId, "사용자 id를 입력해주세요");
    }
}
