package delilah.infrastructure.repositories.cache;

import delilah.infrastructure.repositories.CachedUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Cache<T> {

    private final ArrayList<CachedEntity<T>> cachedEntities = new ArrayList<>();
    private final Clock clock;

    public Cache(Clock clock) {
        this.clock = clock;
    }

    public T findById(String searchId) {

        Optional<CachedEntity<T>> entity = cachedEntities.stream().filter(e -> e.getSearchId().equals(searchId)).findFirst();

        return entity.map(CachedEntity::getEntity).orElse(null);
    }

    public void add(T entity, String searchId, Instant expiry) {

        CachedEntity<T> cachedEntity = new CachedEntity<>(entity, searchId, expiry);

        if (Objects.nonNull(findById(searchId))) {
            this.remove(searchId);
        }

        cachedEntities.add(cachedEntity);
    }

    private void remove(String searchId) {

        cachedEntities.stream().filter(e -> e.getSearchId().equals(searchId)).findFirst()
                .map(cachedEntities::remove);
    }

    @Scheduled(fixedDelay = CachedUser.MAX_MILLIS_LIFETIME)
    public void pruneCache() {

        List<CachedEntity<T>> expiredUsers = cachedEntities.stream().filter(c -> c.isExpired(clock)).collect(Collectors.toList());

        cachedEntities.removeAll(expiredUsers);
    }

}
