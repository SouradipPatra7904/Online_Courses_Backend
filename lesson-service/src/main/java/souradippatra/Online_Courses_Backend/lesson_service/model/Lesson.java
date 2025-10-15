package souradippatra.Online_Courses_Backend.lesson_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer durationMinutes;  // Duration in minutes
    private String resourcesUrl;

    @Column(name = "course_id", nullable = false)
    private Long courseId;  // Link to course-service
}
