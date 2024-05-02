package api.v1.ide.container.service;

import api.v1.ide.container.controller.dto.CodeResult;
import api.v1.ide.container.controller.dto.ContainerCreateDto;
import api.v1.ide.container.domain.Container;
import api.v1.ide.container.domain.ContainerActionHelper;
import api.v1.ide.container.repository.ContainerRepository;
import api.v1.ide.container.service.dto.CreateContainerUserDto;
import api.v1.ide.container.service.dto.SourceCompileDto;
import api.v1.service.UserService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DockerService implements ContainerService{

    private final UserService userService;
    private final DockerClient dockerClient;
    private final ContainerRepository containerRepository;

    @Override
    public ContainerCreateDto createContainer(CreateContainerUserDto createContainerUserDto) {
        final Long userId = createContainerUserDto.userId();

        if (!userService.existsByUserId(userId)) {
            throw new IllegalArgumentException(userId + "의 사용자가 존재하지 않습니다.");
        }
        if (containerRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("이미 생성된 컨테이너가 존재합니다.");
        }

        CreateContainerResponse response = dockerClient.createContainerCmd(ContainerActionHelper.JAVA_COMPILER_IMG)
                .withName(UUID.randomUUID().toString())
                .exec();

        Container container = createContainer(response, userId);
        containerRepository.save(container);

        return new ContainerCreateDto(container.getId(), userId);
    }

    private Container createContainer(CreateContainerResponse response, Long userId) {
        return Container.builder()
                .containerId(response.getId())
                .userId(userId)
                .build();
    }

    @Override
    public CodeResult compileSourceInContainer(SourceCompileDto sourceCompileDto) {
        Container container = containerRepository.findByUserId(sourceCompileDto.userId())
                .orElseThrow(() -> new IllegalArgumentException("컨테이너가 존재하지 않습니다."));

        dockerClient.startContainerCmd(container.getContainerId()).exec();

        return compileJava(container.getContainerId(), sourceCompileDto.source());
    }

    private CodeResult compileJava(String containerId, String source) {
        OutputStream outputStream = new ByteArrayOutputStream();
        OutputStream errorStream = new ByteArrayOutputStream();

        try {
            boolean sourceSaved = saveSourceCodeInContainer(containerId, source, outputStream, errorStream);
            boolean compileSuccess = compileSaveCodeInContainer(containerId, outputStream, errorStream);
            boolean runSuccess = runCompileCdeInContainer(containerId, outputStream, errorStream);

            if (!sourceSaved || !compileSuccess || !runSuccess) {
                return new CodeResult(null, "Code execution failed.");
            }
            return new CodeResult(getMessageFromOutputStream(outputStream), getMessageFromOutputStream(errorStream));
        } catch (InterruptedException e) {
            return new CodeResult(null,"Execution interrupted: " + e.getMessage());
        }
    }

    private boolean saveSourceCodeInContainer(
            String containerId,
            String source,
            OutputStream outputStream,
            OutputStream errorStream
    ) throws InterruptedException
    {
        ExecCreateCmdResponse saveSourceResponse = dockerClient.execCreateCmd(containerId)
                .withCmd(ContainerActionHelper.getSaveCommandFormSource(source))
                .exec();

        return dockerClient.execStartCmd(saveSourceResponse.getId())
                .exec(new ExecStartResultCallback(outputStream, errorStream)).awaitCompletion(5, TimeUnit.SECONDS);
    }

    private boolean compileSaveCodeInContainer(String containerId, OutputStream outputStream, OutputStream errorStream) throws InterruptedException {
        ExecCreateCmdResponse compileResponse = dockerClient.execCreateCmd(containerId)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .withCmd(ContainerActionHelper.getCompileCommand())
                .exec();

        return dockerClient.execStartCmd(compileResponse.getId())
                .exec(new ExecStartResultCallback(outputStream, errorStream))
                .awaitCompletion(10, TimeUnit.SECONDS);
    }

    private boolean runCompileCdeInContainer(String containerId, OutputStream outputStream, OutputStream errorStream) throws InterruptedException {
        ExecCreateCmdResponse runResponse = dockerClient.execCreateCmd(containerId)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .withCmd(ContainerActionHelper.getRunCommand())
                .exec();

        return dockerClient.execStartCmd(runResponse.getId())
                .exec(new ExecStartResultCallback(outputStream, errorStream))
                .awaitCompletion(60, TimeUnit.SECONDS);
    }

    private String getMessageFromOutputStream(OutputStream outputStream) {
        return Arrays.stream(outputStream.toString().split("(?=\n)"))
                .map(String::trim)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
