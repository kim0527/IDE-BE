package api.v1.ide.container.controller.dto;

public record CodeResult(
        String standardOutput,
        String standardError
) {
}