package delilah.infrastructure.repositories.mongoDB;

import delilah.domain.models.notification.NotificationSubscription;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MongoUserRepository extends MongoRepositoryImpl<User, String> implements UserRepository {

    @Override
    public User findById(Object id) {
        return mongoTemplate.findById(id, User.class);
    }

    @Override
    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public User findByDiscordId(String id) {
        return mongoTemplate.findAll(User.class).stream().filter(u -> u.getDiscordId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<User> findBySubscriptions(List<NotificationSubscription> subscriptions) {
        return findAll().stream().filter(u -> !u.getMatchingSubscriptions(subscriptions).isEmpty()).collect(Collectors.toList());
    }
}
