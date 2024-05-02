package api.v1.ide.container.service.dto;

public record SourceCompileDto(
        String source,
        Long userId
) {
}
