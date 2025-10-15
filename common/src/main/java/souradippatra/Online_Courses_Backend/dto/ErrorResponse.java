package souradippatra.Online_Courses_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;

/**
 * Error payload for exception responses.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String details;
    private Instant timestamp = Instant.now();
}
