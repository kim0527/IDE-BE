package api.v1.ide.container.service;

import api.v1.ide.container.controller.dto.CodeResult;
import api.v1.ide.container.controller.dto.ContainerCreateDto;
import api.v1.ide.container.service.dto.CreateContainerUserDto;
import api.v1.ide.container.service.dto.SourceCompileDto;
import org.springframework.stereotype.Service;

@Service
public interface ContainerService {
    CodeResult compileSourceInContainer(SourceCompileDto sourceCompileDto);

    ContainerCreateDto createContainer(CreateContainerUserDto createContainerUserDto);
}
