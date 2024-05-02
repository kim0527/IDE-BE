package api.v1.service;

import api.v1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public boolean existsByUserId(Long userId) {
        return userRepository.existsById(userId);
    }
}
