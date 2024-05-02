package api.v1.ide.container.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "docker")
@Getter @Setter
public class DockerProfile {
    private String email;
    private String password;
    private String username;
    private String host;
}
