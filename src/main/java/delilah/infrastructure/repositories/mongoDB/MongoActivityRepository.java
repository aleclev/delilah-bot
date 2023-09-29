package delilah.infrastructure.repositories.mongoDB;

import delilah.domain.models.groupEvent.Activity;
import delilah.infrastructure.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MongoActivityRepository extends MongoRepositoryImpl<Activity, String> implements ActivityRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Activity findById(Object id) {
        return mongoTemplate.findById(id, Activity.class);
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
