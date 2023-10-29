package delilah.infrastructure.repositories.mongoDB;

import delilah.domain.exceptions.user.UserNotFoundException;
import delilah.domain.models.notification.NotificationSubscription;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import delilah.infrastructure.repositories.cache.Cache;
import lombok.Getter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Getter
public class MongoUserRepository extends MongoRepositoryImpl<User, String> implements UserRepository {

    private final Cache<User> userCache;
    private final Clock clock;

    public MongoUserRepository(MongoTemplate mongoTemplate, Cache<User> userCache, Clock clock) {
        super(mongoTemplate);
        this.userCache = userCache;
        this.clock = clock;
    }

    @Override
    public User findById(Object id) {
        Query discordIdMatch = new Query();
        discordIdMatch.addCriteria(Criteria.where("discordId").is(id));
        User user = mongoTemplate.findOne(discordIdMatch, User.class);

        if (Objects.isNull(user))
            throw new UserNotFoundException("Error! User not found. You may need to register.");

        userCache.add(user, user.getDiscordId(), clock.instant().plusMillis(60_000));

        return user;
    }

    @Override
    public User findById(Object id, boolean searchCache) {

        if (searchCache) {
            User user = userCache.findById((String) id);

            if (user != null)
                return user;
        }

        return findById(id);
    }

    @Override
    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public List<User> findBySubscriptions(List<NotificationSubscription> subscriptions) {
        return findAll().stream().filter(u -> !u.getMatchingSubscriptions(subscriptions).isEmpty()).collect(Collectors.toList());
    }

}
