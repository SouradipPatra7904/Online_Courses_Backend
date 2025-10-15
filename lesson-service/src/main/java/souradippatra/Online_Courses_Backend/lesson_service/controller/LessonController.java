package souradippatra.Online_Courses_Backend.lesson_service.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import souradippatra.Online_Courses_Backend.lesson_service.dto.LessonDto;
import souradippatra.Online_Courses_Backend.dto.ApiResponse;
import souradippatra.Online_Courses_Backend.lesson_service.service.LessonService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public ApiResponse<EntityModel<LessonDto>> createLesson(@RequestBody LessonDto dto) {
        LessonDto lesson = lessonService.createLesson(dto);
        EntityModel<LessonDto> model = EntityModel.of(lesson);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LessonController.class)
                .getLesson(lesson.getId())).withSelfRel());
        return ApiResponse.success("Lesson created successfully", model);
    }

    @GetMapping("/{id}")
    public ApiResponse<EntityModel<LessonDto>> getLesson(@PathVariable Long id) {
        LessonDto lesson = lessonService.getLesson(id);
        EntityModel<LessonDto> model = EntityModel.of(lesson);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LessonController.class)
                .getLesson(id)).withSelfRel());
        return ApiResponse.success("Lesson fetched successfully", model);
    }

    @GetMapping("/course/{courseId}")
    public ApiResponse<CollectionModel<EntityModel<LessonDto>>> getLessonsByCourse(@PathVariable Long courseId) {
        List<EntityModel<LessonDto>> lessons = lessonService.getLessonsByCourse(courseId).stream()
                .map(lesson -> {
                    EntityModel<LessonDto> model = EntityModel.of(lesson);
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LessonController.class)
                            .getLesson(lesson.getId())).withSelfRel());
                    return model;
                }).collect(Collectors.toList());
        return ApiResponse.success("Lessons fetched successfully", CollectionModel.of(lessons));
    }

    @PutMapping("/{id}")
    public ApiResponse<EntityModel<LessonDto>> updateLesson(@PathVariable Long id, @RequestBody LessonDto dto) {
        LessonDto updated = lessonService.updateLesson(id, dto);
        EntityModel<LessonDto> model = EntityModel.of(updated);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LessonController.class)
                .getLesson(id)).withSelfRel());
        return ApiResponse.success("Lesson updated successfully", model);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ApiResponse.success("Lesson deleted successfully", null);
    }
}
