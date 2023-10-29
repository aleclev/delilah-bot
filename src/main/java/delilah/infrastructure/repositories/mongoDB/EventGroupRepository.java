package delilah.infrastructure.repositories.mongoDB;

import delilah.domain.models.groupEvent.GroupEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventGroupRepository extends MongoRepositoryImpl<GroupEvent, String> implements delilah.infrastructure.repositories.EventGroupRepository {

    public EventGroupRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public GroupEvent findById(Object id) {
        return mongoTemplate.findById(id, GroupEvent.class);
    }

    @Override
    public GroupEvent findById(Object id, boolean searchCache) {
        return findById(id);
    }

    @Override
    public List<GroupEvent> findAll() {
        return mongoTemplate.findAll(GroupEvent.class);
    }
}
