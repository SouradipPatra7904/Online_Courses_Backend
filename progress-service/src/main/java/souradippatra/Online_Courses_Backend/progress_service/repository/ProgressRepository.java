package souradippatra.Online_Courses_Backend.progress_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import souradippatra.Online_Courses_Backend.progress_service.model.Progress;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findByUserId(Long userId);
    List<Progress> findByCourseId(Long courseId);
    Optional<Progress> findByUserIdAndCourseId(Long userId, Long courseId);
}
