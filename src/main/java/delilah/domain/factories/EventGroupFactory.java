package delilah.domain.factories;

import delilah.domain.models.lookingForGroup.Activity;
import delilah.domain.models.lookingForGroup.EventGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventGroupFactory {

    @Autowired
    Clock clock;

    public EventGroup createEventGroup(String id, String ownerId, Activity activity, String description, Integer maxSize) {

        List<String> participantsId = new ArrayList<>();
        participantsId.add(ownerId);
        Instant lastActivity = clock.instant();
        return new EventGroup(id, ownerId, activity, description, participantsId, new ArrayList<>(), maxSize, lastActivity);
    }
}
