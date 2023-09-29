package delilah.api.rest;

import delilah.domain.models.groupEvent.Activity;
import delilah.infrastructure.repositories.ActivityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityRepository activityRepository;

    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @GetMapping("/all")
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
}
