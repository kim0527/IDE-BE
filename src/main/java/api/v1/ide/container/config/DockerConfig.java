package api.v1.ide.container.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class DockerConfig {

    private final DockerProfile dockerProfile;

    @Bean
    public DockerClient dockerClient() {
        final DockerClientConfig custom = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerProfile.getHost())
                .withDockerTlsVerify(false)
                .withRegistryUsername(dockerProfile.getUsername())
                .withRegistryPassword(dockerProfile.getPassword())
                .withRegistryEmail(dockerProfile.getEmail())
                .build();

        final DockerHttpClient dockerHttpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(custom.getDockerHost())
                .maxConnections(20)
                .connectionTimeout(Duration.ofSeconds(3))
                .responseTimeout(Duration.ofSeconds(15))
                .build();

        return DockerClientImpl.getInstance(custom, dockerHttpClient);
    }

}
