package souradippatra.Online_Courses_Backend.progress_service.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import souradippatra.Online_Courses_Backend.progress_service.dto.ProgressDto;
import souradippatra.Online_Courses_Backend.progress_service.service.ProgressService;
import souradippatra.Online_Courses_Backend.progress_service.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping
    public ApiResponse<EntityModel<ProgressDto>> createOrUpdate(@RequestBody ProgressDto dto) {
        ProgressDto progress = progressService.createOrUpdateProgress(dto);
        EntityModel<ProgressDto> model = EntityModel.of(progress);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProgressController.class)
                .getProgress(progress.getId())).withSelfRel());
        return ApiResponse.success("Progress saved", model);
    }

    @GetMapping("/{id}")
    public ApiResponse<EntityModel<ProgressDto>> getProgress(@PathVariable Long id) {
        ProgressDto progress = progressService.getProgress(id);
        EntityModel<ProgressDto> model = EntityModel.of(progress);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProgressController.class)
                .getProgress(id)).withSelfRel());
        return ApiResponse.success("Progress fetched", model);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<CollectionModel<EntityModel<ProgressDto>>> getByUser(@PathVariable Long userId) {
        List<EntityModel<ProgressDto>> models = progressService.getProgressByUser(userId)
                .stream()
                .map(p -> EntityModel.of(p,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProgressController.class)
                                .getProgress(p.getId())).withSelfRel()))
                .collect(Collectors.toList());
        return ApiResponse.success("User progress fetched", CollectionModel.of(models));
    }

    @GetMapping("/course/{courseId}")
    public ApiResponse<CollectionModel<EntityModel<ProgressDto>>> getByCourse(@PathVariable Long courseId) {
        List<EntityModel<ProgressDto>> models = progressService.getProgressByCourse(courseId)
                .stream()
                .map(p -> EntityModel.of(p,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProgressController.class)
                                .getProgress(p.getId())).withSelfRel()))
                .collect(Collectors.toList());
        return ApiResponse.success("Course progress fetched", CollectionModel.of(models));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
        return ApiResponse.success("Progress deleted", null);
    }
}
