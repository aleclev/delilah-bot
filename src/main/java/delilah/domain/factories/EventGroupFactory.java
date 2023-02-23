package delilah.domain.factories;

import delilah.domain.models.lookingForGroup.Activity;
import delilah.domain.models.lookingForGroup.EventGroup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class EventGroupFactory {

    public EventGroup createEventGroup(String ownerId, Activity activity, String description, Integer maxSize) {

        List<String> participantsId = new ArrayList<>();
        participantsId.add(ownerId);
        String id = UUID.randomUUID().toString();
        return new EventGroup(id, ownerId, activity, description, participantsId, new ArrayList<>(), maxSize);
    }
}
