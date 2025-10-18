package souradippatra.Online_Courses_Backend.enrollment_service.controller;

import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import souradippatra.Online_Courses_Backend.enrollment_service.dto.EnrollmentDto;
import souradippatra.Online_Courses_Backend.enrollment_service.response.ApiResponse;
import souradippatra.Online_Courses_Backend.enrollment_service.service.EnrollmentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<EntityModel<EnrollmentDto>> create(@RequestBody EnrollmentDto dto) {
        EnrollmentDto created = service.createEnrollment(dto);
        EntityModel<EnrollmentDto> model = EntityModel.of(created);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnrollmentController.class)
                .getEnrollment(created.getId())).withSelfRel());
        return ApiResponse.success("Enrollment created", model);
    }

    @GetMapping
    public ApiResponse<CollectionModel<EntityModel<EnrollmentDto>>> getAll() {
        List<EntityModel<EnrollmentDto>> models = service.getAllEnrollments().stream()
                .map(e -> EntityModel.of(e,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnrollmentController.class)
                                .getEnrollment(e.getId())).withSelfRel()))
                .collect(Collectors.toList());
        return ApiResponse.success("All enrollments", CollectionModel.of(models));
    }

    @GetMapping("/{id}")
    public ApiResponse<EntityModel<EnrollmentDto>> getEnrollment(@PathVariable Long id) {
        EnrollmentDto dto = service.getEnrollmentById(id);
        EntityModel<EnrollmentDto> model = EntityModel.of(dto);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnrollmentController.class)
                .getEnrollment(id)).withSelfRel());
        return ApiResponse.success("Enrollment details", model);
    }

    @PatchMapping("/{id}/status/{status}")
    public ApiResponse<EntityModel<EnrollmentDto>> updateStatus(@PathVariable Long id, @PathVariable String status) {
        EnrollmentDto updated = service.updateStatus(id, status);
        EntityModel<EnrollmentDto> model = EntityModel.of(updated);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnrollmentController.class)
                .getEnrollment(id)).withSelfRel());
        return ApiResponse.success("Enrollment status updated", model);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.deleteEnrollment(id);
        return ApiResponse.success("Enrollment deleted", null);
    }
}
