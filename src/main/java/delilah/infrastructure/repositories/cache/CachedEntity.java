package delilah.infrastructure.repositories.cache;

import lombok.Getter;

import java.time.Clock;
import java.time.Instant;

@Getter
public class CachedEntity<T> {

    private final T entity;
    private final String searchId;
    private final Instant expiry;

    public CachedEntity(T entity, String searchId, Instant expiry) {
        this.entity = entity;
        this.searchId = searchId;
        this.expiry = expiry;
    }

    public T getEntity() {
        return entity;
    }

    public boolean isExpired(Clock clock) {
        return clock.instant().isAfter(expiry);
    }
}
