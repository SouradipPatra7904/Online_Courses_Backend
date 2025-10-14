package souradippatra.Online_Courses_Backend.course_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import souradippatra.Online_Courses_Backend.course_service.model.Course;
import souradippatra.Online_Courses_Backend.course_service.service.CourseService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Course>> getCourse(@PathVariable Long id) {
        return courseService.findById(id)
                .map(course -> {
                    EntityModel<Course> model = EntityModel.of(course);
                    model.add(linkTo(methodOn(CourseController.class).getCourse(id)).withSelfRel());
                    model.add(linkTo(methodOn(CourseController.class).getAllCourses()).withRel("all-courses"));
                    return ResponseEntity.ok(model);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updated) {
        return courseService.findById(id)
                .map(existing -> {
                    updated.setId(id);
                    return ResponseEntity.ok(courseService.save(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

