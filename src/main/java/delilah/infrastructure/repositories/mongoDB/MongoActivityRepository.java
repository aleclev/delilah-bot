package delilah.infrastructure.repositories.mongoDB;

import delilah.domain.models.groupEvent.Activity;
import delilah.infrastructure.repositories.ActivityRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MongoActivityRepository extends MongoRepositoryImpl<Activity, String> implements ActivityRepository {

    private MongoTemplate mongoTemplate;

    public MongoActivityRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public Activity findById(Object id) {
        return mongoTemplate.findById(id, Activity.class);
    }

    @Override
    public Activity findById(Object id, boolean searchCache) {
        return findById(id);
    }

    @Override
    public List<Activity> findAll() {
        return mongoTemplate.findAll(Activity.class);
    }

    @Override
    public List<Activity> searchByName(String name) {
        return findAll().stream().filter(a -> a.getShortName().toLowerCase().contains(name.toLowerCase()) || a.getLongName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }
}
