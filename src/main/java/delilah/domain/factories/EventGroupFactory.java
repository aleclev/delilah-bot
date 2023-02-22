package delilah.domain.factories;

import delilah.domain.lookingForGroup.EventGroup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class EventGroupFactory {

    public EventGroup createEventGroup(String ownerId, String title, String description, Integer maxSize) {

        List<String> participantsId = new ArrayList<>();
        participantsId.add(ownerId);
        String id = UUID.randomUUID().toString();
        return new EventGroup(id, ownerId, title, description, participantsId, new ArrayList<>(), maxSize);
    }
}
