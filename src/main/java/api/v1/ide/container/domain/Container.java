package api.v1.ide.container.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private Long userId;
    @Column(nullable = false)
    private String containerId;

    @Builder
    private Container(Long userId, String containerId) {
        this.userId = userId;
        this.containerId = containerId;
    }
}
