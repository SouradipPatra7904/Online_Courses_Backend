package souradippatra.Online_Courses_Backend.user_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class WebConfig {
    // Enables HAL-based HATEOAS globally for this service
}
