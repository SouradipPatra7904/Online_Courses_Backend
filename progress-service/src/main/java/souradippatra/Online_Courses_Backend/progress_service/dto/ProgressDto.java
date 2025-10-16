package souradippatra.Online_Courses_Backend.progress_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressDto {
    private Long id;
    private Long userId;
    private Long courseId;
    private int lessonsCompleted;
    private int totalLessons;
    private double completionPercentage;
    private LocalDateTime lastAccessed;
}
