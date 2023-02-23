package delilah.infrastructure.repositories.ram;

import delilah.domain.models.lookingForGroup.EventGroup;
import delilah.infrastructure.repositories.EventGroupRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventGroupRepositoryInMemory implements EventGroupRepository {

    private List<EventGroup> eventGroups = new ArrayList<>();

    @Override
    public void save(EventGroup object) {
        eventGroups.add(object);
    }

    @Override
    public EventGroup findById(Object id) {
        return eventGroups.stream().filter(g -> g.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<EventGroup> findAll() {
        return null;
    }
}
