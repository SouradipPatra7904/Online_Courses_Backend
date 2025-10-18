package souradippatra.Online_Courses_Backend.enrollment_service.service;

import org.springframework.stereotype.Service;
import souradippatra.Online_Courses_Backend.enrollment_service.dto.EnrollmentDto;
import souradippatra.Online_Courses_Backend.enrollment_service.model.Enrollment;
import souradippatra.Online_Courses_Backend.enrollment_service.exception.ResourceNotFoundException;
import souradippatra.Online_Courses_Backend.enrollment_service.repository.EnrollmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public EnrollmentDto createEnrollment(EnrollmentDto dto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(dto.getUserId());
        enrollment.setCourseId(dto.getCourseId());
        enrollment = enrollmentRepository.save(enrollment);
        return mapToDto(enrollment);
    }

    public List<EnrollmentDto> getAllEnrollments() {
        return enrollmentRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public EnrollmentDto getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id " + id));
        return mapToDto(enrollment);
    }

    public List<EnrollmentDto> getEnrollmentsByUser(Long userId) {
        return enrollmentRepository.findByUserId(userId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<EnrollmentDto> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public EnrollmentDto updateStatus(Long id, String status) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id " + id));

        enrollment.setStatus(Enrollment.EnrollmentStatus.valueOf(status.toUpperCase()));

        if (enrollment.getStatus() == Enrollment.EnrollmentStatus.COMPLETED)
            enrollment.setCompletedAt(java.time.LocalDateTime.now());

        enrollment = enrollmentRepository.save(enrollment);
        return mapToDto(enrollment);
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    private EnrollmentDto mapToDto(Enrollment e) {
        return new EnrollmentDto(
                e.getId(), e.getUserId(), e.getCourseId(),
                e.getStatus().name(), e.getEnrolledAt(), e.getCompletedAt()
        );
    }
}
