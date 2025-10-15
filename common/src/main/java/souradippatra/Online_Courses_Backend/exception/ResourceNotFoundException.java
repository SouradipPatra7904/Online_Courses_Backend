package souradippatra.Online_Courses_Backend.exception;

/**
 * Thrown when an entity cannot be found in a repository.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
