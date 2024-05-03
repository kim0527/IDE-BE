package api.v1.repository;

import api.v1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User,Long> {
    boolean existsByNickname(String nickname);
    boolean existsByKakaoId(Integer kakaoId);
    Optional<User> findByKakaoId(Integer kakaoId);

}
