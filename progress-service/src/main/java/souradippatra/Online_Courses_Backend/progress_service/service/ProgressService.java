package souradippatra.Online_Courses_Backend.progress_service.service;

import org.springframework.stereotype.Service;
import souradippatra.Online_Courses_Backend.progress_service.dto.ProgressDto;
import souradippatra.Online_Courses_Backend.progress_service.exception.ResourceNotFoundException;
import souradippatra.Online_Courses_Backend.progress_service.model.Progress;
import souradippatra.Online_Courses_Backend.progress_service.repository.ProgressRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public ProgressDto createOrUpdateProgress(ProgressDto dto) {
        Progress progress = progressRepository.findByUserIdAndCourseId(dto.getUserId(), dto.getCourseId())
                .orElse(new Progress(null, dto.getUserId(), dto.getCourseId(), 0, dto.getTotalLessons(), 0.0, null));

        progress.setLessonsCompleted(dto.getLessonsCompleted());
        progress.setTotalLessons(dto.getTotalLessons());

        progress = progressRepository.save(progress);
        return mapToDto(progress);
    }

    public ProgressDto getProgress(Long id) {
        Progress p = progressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progress not found with id: " + id));
        return mapToDto(p);
    }

    public List<ProgressDto> getProgressByUser(Long userId) {
        return progressRepository.findByUserId(userId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<ProgressDto> getProgressByCourse(Long courseId) {
        return progressRepository.findByCourseId(courseId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public void deleteProgress(Long id) {
        progressRepository.deleteById(id);
    }

    private ProgressDto mapToDto(Progress p) {
        return new ProgressDto(
                p.getId(),
                p.getUserId(),
                p.getCourseId(),
                p.getLessonsCompleted(),
                p.getTotalLessons(),
                p.getCompletionPercentage(),
                p.getLastAccessed()
        );
    }
}
