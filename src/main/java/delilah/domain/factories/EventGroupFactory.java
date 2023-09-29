package delilah.domain.factories;

import delilah.domain.models.groupEvent.Activity;
import delilah.domain.models.groupEvent.GroupEvent;
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

    public GroupEvent createEventGroup(String id, String ownerId, Activity activity, String description,
                                       Integer maxSize, Instant startTime) {

        List<String> participantsId = new ArrayList<>();
        participantsId.add(ownerId);
        Instant lastActivity = clock.instant();
        return new GroupEvent(id, ownerId, activity, description, participantsId, new ArrayList<>(), maxSize,
                lastActivity, startTime);
    }
}
