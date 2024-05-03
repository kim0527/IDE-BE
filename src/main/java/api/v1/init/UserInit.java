package api.v1.init;

import api.v1.domain.User;
import api.v1.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Profile("default")
@Component
@Slf4j
@RequiredArgsConstructor
public class UserInit {

    private final InitUserService initUserService;

    @PostConstruct
    public void init() {
        initUserService.init();
    }


    @Service
    @RequiredArgsConstructor
    public static class InitUserService {
        private final UserRepository userRepository;

        private void init() {
            final User user = User.builder()
                    .kakaoId(1)
                    .name("test")
                    .profileImg("test")
                    .nickname("test")
                    .birthDate(LocalDate.now())
                    .role("ROLE_USER")
                    .build();

            userRepository.save(user);
            log.info("userId={}", user.getId());
        }

    }
}
