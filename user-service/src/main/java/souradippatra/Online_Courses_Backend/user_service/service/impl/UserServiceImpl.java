package souradippatra.Online_Courses_Backend.user_service.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import souradippatra.Online_Courses_Backend.user_service.dto.UserDTO;
import souradippatra.Online_Courses_Backend.user_service.model.User;
import souradippatra.Online_Courses_Backend.user_service.repository.UserRepository;
import souradippatra.Online_Courses_Backend.user_service.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDTO createUser(UserDTO dto) {
        User user = new User();
        user.setEmail(dto.email());
        user.setFullName(dto.fullName());
        user.setRole(User.Role.valueOf(dto.role().toUpperCase()));
        user.setBio(dto.bio());
        repo.save(user);
        return mapToDTO(user);
    }

    @Override
    public UserDTO getUserById(UUID id) {
        return repo.findById(id)
                   .map(this::mapToDTO)
                   .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserDTO> getUsersByRole(String role) {
        return repo.findByRole(User.Role.valueOf(role.toUpperCase()))
                   .stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return repo.findAll().stream().map(this::mapToDTO).toList();
    }

    private UserDTO mapToDTO(User u) {
        return new UserDTO(u.getId(), u.getEmail(), u.getFullName(), u.getRole().name(), u.getBio());
    }
}
