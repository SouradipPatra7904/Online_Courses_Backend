package souradippatra.Online_Courses_Backend.user_service.hateoas;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import souradippatra.Online_Courses_Backend.user_service.controller.UserController;
import souradippatra.Online_Courses_Backend.user_service.dto.UserDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> {

    @Override
    public @NonNull EntityModel<UserDTO> toModel(@NonNull UserDTO user) {
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(user.id())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers(user.role())).withRel("users"));
    }
}
