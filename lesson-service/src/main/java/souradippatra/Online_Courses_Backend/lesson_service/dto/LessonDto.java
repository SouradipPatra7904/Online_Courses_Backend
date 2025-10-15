package souradippatra.Online_Courses_Backend.lesson_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    private Long id;
    private String title;
    private String description;
    private Integer durationMinutes;
    private Long courseId;
    private String resourcesUrl;
}
