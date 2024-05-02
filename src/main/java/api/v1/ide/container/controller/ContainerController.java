package api.v1.ide.container.controller;

import api.v1.ide.container.controller.dto.*;
import api.v1.ide.container.service.ContainerService;
import api.v1.ide.container.service.dto.CreateContainerUserDto;
import api.v1.ide.container.service.dto.SourceCompileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContainerController {

    private final ContainerService containerService;

    @PostMapping("/containers")
    public ResponseEntity<ContainerCreateDto> createContainer(@RequestBody ContainerCreateRequest containerCreateRequest) {
        CreateContainerUserDto createContainerUserDto = new CreateContainerUserDto(containerCreateRequest.userId());
        ContainerCreateDto container = containerService.createContainer(createContainerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(container);
    }

    @PostMapping("/containers/run")
    public ResponseEntity<CodeResult> runSourceFile(@RequestBody SourceRequest sourceRequest) {
        SourceCompileDto sourceCompileDto = getSourceCompileDtoFromRequest(sourceRequest);

        CodeResult codeResult = containerService.compileSourceInContainer(sourceCompileDto);
        return ResponseEntity.ok(codeResult);
    }

    private SourceCompileDto getSourceCompileDtoFromRequest(SourceRequest sourceRequest) {
        return new SourceCompileDto(sourceRequest.source(), sourceRequest.userId());
    }

}
