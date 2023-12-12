package delilah.infrastructure.repositories.mongoDB;

import delilah.domain.models.groupEvent.GroupEvent;
import delilah.infrastructure.repositories.ConfigVariable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfigVariableRepository extends MongoRepositoryImpl<ConfigVariable, String> implements delilah.infrastructure.repositories.ConfigVariableRepository {

    public ConfigVariableRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public ConfigVariable findById(Object id) {
        return mongoTemplate.findById(id, ConfigVariable.class);
    }

    @Override
    public ConfigVariable findById(Object id, boolean searchCache) {
        return findById(id);
    }

    @Override
    public List<ConfigVariable> findAll() {
        return mongoTemplate.findAll(ConfigVariable.class);
    }
}
