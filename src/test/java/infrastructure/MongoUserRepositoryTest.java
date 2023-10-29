package infrastructure;

import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.cache.Cache;
import delilah.infrastructure.repositories.cache.CachedEntity;
import delilah.infrastructure.repositories.mongoDB.MongoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import testUtil.UserBuilder;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
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
        Mockito.when(mongoTemplate.findOne(any(), eq(User.class))).thenReturn(user);
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(60_000));

        mongoUserRepository.findById(id);

        Mockito.verify(userCache).add(user, user.getDiscordId(), Instant.ofEpochMilli(120_000));
    }

    @Test
    public void givenExpiredCachedUser_whenPurgingCache_thenUserRemovedFromCache() {


    }
}
