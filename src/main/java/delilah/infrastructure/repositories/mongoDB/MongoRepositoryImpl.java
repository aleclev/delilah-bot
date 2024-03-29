package delilah.infrastructure.repositories.mongoDB;

import delilah.infrastructure.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public abstract class MongoRepositoryImpl<T, ID> implements Repository<T> {

    protected MongoTemplate mongoTemplate;

    public MongoRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void save(T object) {
        mongoTemplate.save(object);
    }

    @Override
    public void delete(T object) {
        mongoTemplate.remove(object);
    }
}
