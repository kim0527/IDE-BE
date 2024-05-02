package api.v1.repository;

import api.v1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Long> {
    boolean existsByNickname(String nickname);
    boolean existsByKakaoId(Long kakaoId);


}
