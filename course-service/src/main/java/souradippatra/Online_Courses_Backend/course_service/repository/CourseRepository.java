package souradippatra.Online_Courses_Backend.course_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import souradippatra.Online_Courses_Backend.course_service.model.Course;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCategory(String category);
    List<Course> findByLevel(String level);
}

