package souradippatra.Online_Courses_Backend.course_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import souradippatra.Online_Courses_Backend.course_service.model.Course;
import souradippatra.Online_Courses_Backend.course_service.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;

    public List<Course> findAll() {
        return repository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return repository.findById(id);
    }

    public Course save(Course course) {
        return repository.save(course);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Course> findByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<Course> findByLevel(String level) {
        return repository.findByLevel(level);
    }
}

