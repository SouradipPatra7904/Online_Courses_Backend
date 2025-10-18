package souradippatra.Online_Courses_Backend.enrollment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {
    private Long id;
    private Long userId;
    private Long courseId;
    private String status;
    private LocalDateTime enrolledAt;
    private LocalDateTime completedAt;
}
