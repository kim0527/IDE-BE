package api.v1.ide.container.repository;

import api.v1.ide.container.domain.Container;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContainerRepository extends JpaRepository<Container, Long> {
    boolean existsByUserId(Long userId);

    Optional<Container> findByUserId(Long userId);
}
