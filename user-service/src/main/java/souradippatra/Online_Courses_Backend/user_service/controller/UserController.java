package souradippatra.Online_Courses_Backend.user_service.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import souradippatra.Online_Courses_Backend.user_service.dto.UserDTO;
import souradippatra.Online_Courses_Backend.user_service.hateoas.UserModelAssembler;
import souradippatra.Online_Courses_Backend.user_service.service.UserService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;
    private final UserModelAssembler assembler;

    public UserController(UserService service, UserModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<UserDTO>> createUser(@RequestBody UserDTO dto) {
        UserDTO created = service.createUser(dto);
        return ResponseEntity
                .created(linkTo(methodOn(UserController.class).getUserById(created.id())).toUri())
                .body(assembler.toModel(created));
    }

    @GetMapping("/{id}")
    public EntityModel<UserDTO> getUserById(@PathVariable UUID id) {
        return assembler.toModel(service.getUserById(id));
    }

    @GetMapping
    public CollectionModel<EntityModel<UserDTO>> getAllUsers(@RequestParam(required = false) String role) {
        List<UserDTO> users = (role != null) ? service.getUsersByRole(role) : service.getAllUsers();
        List<EntityModel<UserDTO>> models = users.stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(models, linkTo(methodOn(UserController.class).getAllUsers(null)).withSelfRel());
    }
}
