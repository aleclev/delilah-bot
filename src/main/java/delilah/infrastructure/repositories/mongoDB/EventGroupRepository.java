package delilah.infrastructure.repositories.mongoDB;

import delilah.domain.models.lookingForGroup.EventGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventGroupRepository extends MongoRepositoryImpl<EventGroup, String> implements delilah.infrastructure.repositories.EventGroupRepository {

    @Override
    public EventGroup findById(Object id) {
        return mongoTemplate.findById(id, EventGroup.class);
    }

    @Override
    public List<EventGroup> findAll() {
        return mongoTemplate.findAll(EventGroup.class);
    }
}
