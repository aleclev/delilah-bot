package delilah.infrastructure.repositories.ram;

import delilah.domain.models.groupEvent.GroupEvent;
import delilah.infrastructure.repositories.EventGroupRepository;

import java.util.ArrayList;
import java.util.List;

public class EventGroupRepositoryInMemory implements EventGroupRepository {

    private List<GroupEvent> groupEvents = new ArrayList<>();

    @Override
    public void save(GroupEvent object) {
        groupEvents.add(object);
    }

    @Override
    public GroupEvent findById(Object id) {
        return groupEvents.stream().filter(g -> g.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public GroupEvent findById(Object id, boolean searchCache) {
        return findById(id);
    }

    @Override
    public void delete(GroupEvent object) {

    }

    @Override
    public List<GroupEvent> findAll() {
        return null;
    }
}
