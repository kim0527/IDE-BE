package api.v1.auth.token;

import api.v1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findTokenByUser(User user);
    Optional<RefreshToken> findByToken(String refreshToken);

}
