package infrastructure;

import delilah.domain.exceptions.user.UserNotFoundException;
import delilah.domain.models.notification.NotificationSubscription;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.cache.Cache;
import delilah.infrastructure.repositories.cache.CachedEntity;
import delilah.infrastructure.repositories.mongoDB.MongoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import testUtil.UserBuilder;

import java.util.List;
import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class MongoUserRepositoryTest {

    private MongoUserRepository mongoUserRepository;
    private MongoTemplate mongoTemplate = Mockito.mock(MongoTemplate.class);
    private Clock clock = Mockito.mock(Clock.class);
    private Cache<User> userCache = Mockito.mock(Cache.class);
    private User user;
    private String id;

    @BeforeEach
    public void setup() {
        user = new UserBuilder().genericUser().build();
        id = user.getDiscordId();
        Mockito.reset(clock, mongoTemplate);
        mongoUserRepository = new MongoUserRepository(mongoTemplate, userCache, clock);
    }

    @Test
    public void whenSaving_thenCallsMongoTemplateSave() {
        mongoUserRepository.save(user);

        Mockito.verify(mongoTemplate).save(user);
    }

    @Test
    public void whenDeleting_thenCallsMongoTemplateRemove() {

        mongoUserRepository.delete(user);

        Mockito.verify(mongoTemplate).remove(user);
    }

    @Test
    public void whenFindingAll_thenCallsMongoTemplateFindAll() {

        mongoUserRepository.findAll();

        Mockito.verify(mongoTemplate).findAll(User.class);
    }


    @Test
    public void whenFetchingUserById_thenUserAddedToCache() {
        Mockito.when(mongoTemplate.findById(id, User.class)).thenReturn(user);
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(60_000));

        mongoUserRepository.findById(id);

        Mockito.verify(userCache).add(user, user.getDiscordId(), Instant.ofEpochMilli(120_000));
    }

    @Test
    public void givenExpiredCachedUser_whenPurgingCache_thenUserRemovedFromCache() {


    }

    @Test
    public void givenUserInCache_whenFindingUserByIdAndSearchingCache_thenCacheIsSearched() {

        Mockito.when(userCache.findById(id)).thenReturn(user);

        mongoUserRepository.findById(id, true);

        Mockito.verify(userCache).findById(id);
    }

    @Test
    public void givenUserNotInCache_whenFindingUserByIdAndSearchingCache_thenSearchesDatabase() {

        Mockito.when(mongoTemplate.findById(id, User.class)).thenReturn(user);
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(60_000));

        mongoUserRepository.findById(id, true);

        Mockito.verify(mongoTemplate).findById(id, User.class);
    }

    @Test
    public void givenUserNotInDatabase_whenFindingUserById_thenThrowsUserNotFoundException() {

        assertThrows(UserNotFoundException.class,
                () -> mongoUserRepository.findById(id));
    }

    @Test
    public void givenUserWithSubscription_whenSearchingBySubscriptions_thenUserIsReturned() {
        NotificationSubscription subscription = new NotificationSubscription("lw");
        User withSubscription =
                new UserBuilder()
                .genericUser()
                .withSubscription(subscription)
                .build();
        Mockito.when(mongoTemplate.findAll(User.class)).thenReturn(List.of(withSubscription, user));

        List<User> result = mongoUserRepository.findBySubscriptions(List.of(subscription));

        assertThat(result).contains(withSubscription);
        assertThat(result).doesNotContain(user);
    }
}
