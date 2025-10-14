package souradippatra.Online_Courses_Backend.user_service.dto;

import java.util.UUID;

public record UserDTO(UUID id, String email, String fullName, String role, String bio) {}
