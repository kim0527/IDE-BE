package api.v1.ide.container.controller.dto;

import org.springframework.util.Assert;

public record ContainerCreateDto(
        Long containerId,
        Long userId
) {
    public ContainerCreateDto {
        Assert.notNull(containerId, "컨테이너 id를 확인해주세요");
        Assert.notNull(userId, "사용자 id가 올바르지 않습니다.");
    }
}
