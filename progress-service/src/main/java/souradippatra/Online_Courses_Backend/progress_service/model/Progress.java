package souradippatra.Online_Courses_Backend.progress_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false)
    private int lessonsCompleted = 0;

    @Column(nullable = false)
    private int totalLessons = 0;

    @Column(nullable = false)
    private double completionPercentage = 0.0;

    private LocalDateTime lastAccessed = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.lastAccessed = LocalDateTime.now();
        if (totalLessons > 0) {
            this.completionPercentage = (lessonsCompleted * 100.0) / totalLessons;
        }
    }
}
