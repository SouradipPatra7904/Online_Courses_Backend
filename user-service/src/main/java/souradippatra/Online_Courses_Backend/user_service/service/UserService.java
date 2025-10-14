package souradippatra.Online_Courses_Backend.user_service.service;

import souradippatra.Online_Courses_Backend.user_service.dto.UserDTO;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(UUID id);
    List<UserDTO> getUsersByRole(String role);
    List<UserDTO> getAllUsers();
}
