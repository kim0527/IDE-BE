package IDE.repository;

import IDE.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Long> {
    boolean existsByNickname(String nickname);
    boolean existsByKakaoId(Long kakaoId);


}
