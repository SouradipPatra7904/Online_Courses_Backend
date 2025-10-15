package souradippatra.Online_Courses_Backend.lesson_service.service;

import org.springframework.stereotype.Service;

import souradippatra.Online_Courses_Backend.exception.ResourceNotFoundException;
import souradippatra.Online_Courses_Backend.lesson_service.dto.LessonDto;
import souradippatra.Online_Courses_Backend.lesson_service.model.Lesson;

import souradippatra.Online_Courses_Backend.lesson_service.repository.LessonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public LessonDto createLesson(LessonDto dto) {
        Lesson lesson = new Lesson(null, dto.getTitle(), dto.getDescription(),
                dto.getDurationMinutes(), dto.getResourcesUrl(), dto.getCourseId());
        lesson = lessonRepository.save(lesson);
        return mapToDto(lesson);
    }

    public LessonDto getLesson(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));
        return mapToDto(lesson);
    }

    public List<LessonDto> getLessonsByCourse(Long courseId) {
        return lessonRepository.findByCourseId(courseId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public LessonDto updateLesson(Long id, LessonDto dto) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));

        lesson.setTitle(dto.getTitle());
        lesson.setDescription(dto.getDescription());
        lesson.setDurationMinutes(dto.getDurationMinutes());
        lesson.setResourcesUrl(dto.getResourcesUrl());
        lesson.setCourseId(dto.getCourseId());

        return mapToDto(lessonRepository.save(lesson));
    }

    public void deleteLesson(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));
        lessonRepository.delete(lesson);
    }

    private LessonDto mapToDto(Lesson lesson) {
        return new LessonDto(lesson.getId(), lesson.getTitle(), lesson.getDescription(),
                lesson.getDurationMinutes(), lesson.getCourseId(), lesson.getResourcesUrl());
    }
}
