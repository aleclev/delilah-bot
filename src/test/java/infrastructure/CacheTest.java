package infrastructure;

import delilah.infrastructure.repositories.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheTest {

    private static final String entity = "entity";
    private static final String searchId = "search_id";
    private Cache<String> cache;
    private Clock clock = Mockito.mock(Clock.class);

    @BeforeEach
    public void setup() {

        cache = new Cache<>(clock);
    }

    @Test
    public void givenEmptyCache_whenAdding_thenEntityAdded() {

        cache.add(entity, searchId, Instant.ofEpochMilli(1000));

        assertThat(cache.findById(searchId)).isEqualTo(entity);
    }

    @Test
    public void givenCacheWithEntity_whenAddingEntityWithSameSearchId_thenCacheEntryIsOverwritten() {

        String newEntity = "newEntity";
        cache.add(entity, searchId, Instant.ofEpochMilli(1000));
        cache.add(newEntity, searchId, Instant.ofEpochMilli(1000));

        assertThat(cache.findById(searchId)).isEqualTo(newEntity);
    }

    @Test
    public void givenCacheWithEntity_whenRemoving_thenEntityIsRemoved() {

        cache.add(entity, searchId, Instant.ofEpochMilli(1000));

        cache.remove(searchId);

        assertThat(cache.findById(searchId)).isNull();
    }

    @Test
    public void givenCacheWithExpiredEntity_whenPruning_thenEntityRemoved() {

        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(1001));
        String otherEntity = "otherEntity";
        String otherSearchId = "otherSearchId";
        cache.add(otherEntity, otherSearchId, Instant.ofEpochMilli(2000));
        cache.add(entity, searchId, Instant.ofEpochMilli(1000));

        cache.pruneCache();

        assertThat(cache.findById(otherSearchId)).isEqualTo(otherEntity);
        assertThat(cache.findById(searchId)).isNull();
    }
}
